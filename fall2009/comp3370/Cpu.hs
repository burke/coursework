module Cpu where

import System (getArgs)
import System.IO
import qualified Data.ByteString as B

type Word = [Int]

data CpuState = CpuState { registers      :: [Word]
                         , programCounter :: Word
                         , codeSpace      :: [Word]
                         , dataSpace      :: [Word]
                         } deriving (Show)

main :: IO ()
main = do
  args <- getArgs
  progH <- openFile (args !! 0) ReadMode
  dataH <- openFile (args !! 1) ReadMode
  progC <- B.hGetContents progH
  dataC <- B.hGetContents dataH
           
  

  print dataC
 
  hClose progH
  hClose dataH

-- A word is 16 bits long, but hSeek skips by 8 bits. 
-- If we multiply the desired word index by two, everything is happy.
words :: Int -> Int
words = (*) 2