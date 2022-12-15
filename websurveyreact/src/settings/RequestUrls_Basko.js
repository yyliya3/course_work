export default class RequestUrls_Basko {
  static SERVER_URL = 'http://localhost:8080'
  static REGISTER = this.SERVER_URL + '/user/register' 
  static LOGIN = this.SERVER_URL + '/user/login'  
  static LOGIN_EXIST = this.SERVER_URL + '/user/exist' 
  static VERIFY_SESSION = this.SERVER_URL + '/user/session/verify' 
  static REMOVE_SESSION = this.SERVER_URL + '/user/session/remove' 
  static GET_AVALIBLE_SURVEYS = this.SERVER_URL + '/user/surveys/uncompleted' 
  static GET_MY_SURVEYS = this.SERVER_URL + '/user/surveys/my' 
  static GET_ALL_USERS = this.SERVER_URL + '/user/all' 
  static RECOVERY = this.SERVER_URL + '/user/recovery'
  static SURVEY = this.SERVER_URL + '/survey' 
  static GET_ALL_QUESTIONS = '/questions' 
  static GET_QUESTIONS_WITH_ANSWERS = '/questions_with_answers' 
  static UPDATE_ANSWERS = '/update_answers' 
  static UPDATE_SURVEY = '/modify_all_survey' 
  static CREATE_SURVEY = this.SERVER_URL + '/survey/create' 
  static DELETE_SURVEY = '/delete' 
  static BAN = this.SERVER_URL + '/user/ban' 
  static UNBAN = this.SERVER_URL + '/user/unban' 
}