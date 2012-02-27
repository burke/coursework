#pragma once

// Stores a key and object to use as node data storage
template <class T>
class Entry {
private:
  int  key;  // K/V pair :: key
  T   *data; // K/V paid :: value

public:

  // Accessors
  int getKey() { return key;  }
  T* getData() { return data; }

  // Constructors
  Entry() {
    this->key  = NULL;
    this->data = NULL;
  }
  Entry(int key, T* data) {
    this->key  = key;
    this->data = data;
  }

  // Destructor
  ~Entry() {
    delete(this->data);
  }
  
};
