import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Basko from '../settings/Cookies_Basko'
import RequestUrls_Basko from '../settings/RequestUrls_Basko'
import Strings_Basko from '../settings/Strings_Basko'
import Question_Basko from '../components/Question_Basko'
import { Box, Button } from '@mui/material'
import toast from 'react-hot-toast'

export default class ViewSurveyPage_Basko extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      questionsList: [],
      answers: []
    }
    
    this.onSaveAnswerClick = this.onSaveAnswerClick.bind(this)
    this.saveAnswer = this.saveAnswer.bind(this)
    this.answerChanged = this.answerChanged.bind(this)
    
    this.loadQuestions()
  }

  loadQuestions = async () => {
    let questions = await fetch(RequestUrls_Basko.SURVEY + '/' + this.props.params.id + RequestUrls_Basko.GET_ALL_QUESTIONS, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'}
    })
    questions = await questions.json()

    this.setState({questionsList: questions})
  }

  onSaveAnswerClick = () => {
    toast.promise(
      this.saveAnswer(), {
         loading: Strings_Basko.SAVING,
         success: <b>{Strings_Basko.ANSWERS_SAVED}</b>,
         error: <b>{Strings_Basko.OCCURED_ERROR}</b>
       }
    )
  }

  saveAnswer = () => {
    return new Promise((resolve, reject) => {
      let answers = []
      this.state.questionsList.forEach(question => {
        answers.push({id: question['id'], answer: this.state.answers[question['id']]})
      })
      
      const request_body = {
        'login': Cookies.get(Cookies_Basko.LOGIN),
        'session_id': Cookies.get(Cookies_Basko.SESSION_ID),
        'answers': answers
      }
    
      fetch(RequestUrls_Basko.SURVEY + '/' + this.props.params.id + RequestUrls_Basko.UPDATE_ANSWERS, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        if (response.status === 200) resolve()
        if (response.status === 423) toast.error(Strings_Basko.YOU_ARE_BANNED)
        reject()
      }).catch(() => {reject()})
    })
  }

  answerChanged = (value, id) => {
    let newAnswers = this.state.answers
    newAnswers[id] = value
    this.setState({answers: newAnswers})
  }
  
  render() {
    const that = this;

    return (
      <div id='ViewSurveyPage'>
        <div id='questionsList'>
          {this.state.questionsList.map(function(question) {
            return(
              <Question_Basko valueChanged={that.answerChanged} question={question}/>
            )
          })}
          <Box sx={{ paddingTop: 4 }} />
        
          <Button variant="outlined" onClick={this.onSaveAnswerClick}>Сохранить ответы</Button>
          <Box sx={{ paddingTop: 20 }} />
        </div>
      </div>
    )
  }
}