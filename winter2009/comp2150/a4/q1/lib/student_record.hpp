#pragma once

using namespace std;

// Key/value pair. Stores a student number and student name.
class StudentRecord {
private:
  string name; // K/V pair :: value
  int    key;  // K/V pair :: key

public:
  StudentRecord(int key, string name) {
    this->key  = key;
    this->name = name;
  }

  ~StudentRecord() {
    //TODO
  }

  int    getKey()  { return key;  }
  string getName() { return name; }
  
  string toString() {
    //TODO
  }

};

