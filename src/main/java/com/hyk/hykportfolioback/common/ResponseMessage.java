package com.hyk.hykportfolioback.common;

public interface ResponseMessage {

  // HTTP Status 200
  String SUCCESS = "Success.";

  // HTTP Status 400
  String VALIDATION_FAILED = "Validation failed.";
  String DUPLICATE_ID = "Duplicate id.";
  String NOT_EXISTED_PROJECT = "This project does not exist.";
  String NOT_EXISTED_POST = "This post does not exist.";
  String EMPTY_FILE = "This file is empty.";

  // HTTP Status 401
  String AUTHORIZATION_FAILED = "Authorization Failed.";

  // HTTP Status 403
  String NO_PERMISSION = "Do not have permission.";

  // HTTP Status 500
  String DATABASE_ERROR = "Database error.";
  String FILE_SAVE_ERROR = "File save error.";

}
