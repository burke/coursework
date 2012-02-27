-----------------------------------------------------------------------------
-- |
-- Module      :  Bioinf.Assignment1.Question3
-- Copyright   :  (c) Burke Libbey 2009
--
-- Calculate the optimal sequence alignment from user-supplied data.
--
-----------------------------------------------------------------------------

module Bioinf.Assignment1.Question3 where

import Bio.Alignment.AAlign -- Align w/ Affine Gap Penalties
import Bio.Alignment.AlignData
import Bio.Sequence.SeqData
import Control.Monad
import qualified Data.ByteString.Lazy as B
import Data.ByteString.Internal (c2w, w2c)
import IO
import Text.Regex

-- | I kind of wish this was just part of IO...
readNum :: IO Integer
readNum = readLn

{-|
  Convert a string of form "GATTACA" to a nucleotide sequence that Bio groks.
  The first argument to Seq is the sequence header. It doesn't seem to
  matter for the parts of the library I'm using, so default to "A".
-}
toSeq :: String -> Sequence Amino
toSeq x = Seq (B.pack $ map c2w "A") (B.pack $ map c2w x) Nothing

{-|
  This part is a bit hairy, I guess. 'global_align' returns (score, editList).
  We're only concerned with editList. We have to call toStrings on it first,
  to get the actual aligned sequences in a nice 2-tuple of strings.
-}
alignedSequences :: (Num a, Ord a) => SubstMx Nuc a -> (a, a) ->
                    Sequence Nuc -> Sequence Nuc -> (String, String)
alignedSequences substMx (sPen, lPen) seq1 seq2 =
    toStrings . snd $ global_align substMx (sPen, lPen) seq1 seq2

{-|
  What we're doing here is reading in the matrix and assuming it's of the form:
     |  A|  C|  G|  T
    A|  n|  n|  n|  n
    C|  n|  n|  n|  n
    G|  n|  n|  n|  n
    T|  n|  n|  n|  n
   Notably, the headers are totally ignored, and the order is treated as ACGT.
   We construct a function that returns the right value for a pair of
   nucleotides. This could be optimized and beautified a fair bit, but my
   haskell-fu is still weak.
-}
parseMatrix :: String -> SubstMx Amino Integer
parseMatrix file = matrix
    where matrix (x, y) = read $ dataRows !! (w2i x) !! (w2i y) :: Integer
          dataRows = map split $ lines file
          split    = splitRegex $ mkRegex " *\\| *"
          w2i = c2i . w2c
          c2i 'A' = 1
          c2i 'C' = 2
          c2i 'G' = 3
          c2i 'T' = 4

{-|
  1. Read the start and length penalties from stdin as integers, one per line.
  2. Read two sequences from stdin, one per line.
  3. Compute the optimal alignment, print the aligned sequences to stdout.

  This is really ugly. I should do something about this.
-}
main :: IO ()
main = do
  putStrLn "File Name?"
  fname         <- getLine
  matrix        <- liftM parseMatrix $ readFile fname
  putStrLn "Gap Start Penalty?"
  startPenalty  <- readNum
  putStrLn "Gap Length Penalty?"
  lengthPenalty <- readNum
  putStrLn "Sequence 1?"
  seq1          <- liftM toSeq getLine
  putStrLn "Sequence 2?"
  seq2          <- liftM toSeq getLine
  let aligned = alignedSequences matrix (startPenalty, lengthPenalty) seq1 seq2
  putStrLn $ fst aligned
  putStrLn $ snd aligned
