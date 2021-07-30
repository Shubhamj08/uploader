# Upload Library

### class constructor takes one parameter which is - context
---
### there are two functions:
---
### fun upload(userId: String, data: Intent?, AlertDialog? = null)
#### this function takes 3 parameters:
#### userId: String - this is needed to add document in the folder of this user
#### data: Intent - this is the data of the chosen document. it is needed to get uri of the document
#### AlertDialog - this is optional, pass this to show uploading progress
---
### fun showDialog(title: String, message: String)
#### this function takes two arguments: title and message to show in the progress dialog