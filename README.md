# DataStore
A base for creating models in MVC apps, especially useful when there are many domain classes.

Domain classes need to extend DataStoreItem and implement the toString function, which will be used when printing the data to a file.
DataStore will also need to be extended with the extended DataStoreItem above as template argument. You will also need to implement the parse(string) function which is the reverse of the DataStoreItem.toString(). ie it converts a string containing the data into the templated type. This is used when reading from files.
