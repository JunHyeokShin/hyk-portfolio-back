package com.hyk.hykportfolioback.common;

public interface ResponseCode {

  // HTTP Status 200
  String SUCCESS = "SU";

  // HTTP Status 400
  String VALIDATION_FAILED = "VF";
  String DUPLICATE_ID = "DI";
  String NOT_EXISTED_PROJECT = "NEP";
  String NOT_EXISTED_POST = "NEP";

  // HTTP Status 403
  String NO_PERMISSION = "NP";

  // HTTP Status 500
  String DATABASE_ERROR = "DBE";

}
