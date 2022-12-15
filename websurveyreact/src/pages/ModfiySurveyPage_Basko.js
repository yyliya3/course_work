import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Basko from '../settings/Cookies_Basko'
import RequestUrls_Basko from '../settings/RequestUrls_Basko'
import Strings_Basko from '../settings/Strings_Basko'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import ModifyQuestion_Basko from '../components/ModifyQuestion_Basko'
import { Box, Button, TextField } from '@mui/material'
import toast from 'react-hot-toast'
import AddIcon from '@mui/icons-material/Add'
import DeleteIcon from '@mui/icons-material/Delete'
import Popup from 'reactjs-popup'

export default function ModifySurveyPage_Basko(props) {
  const [questionsList, setQuestionsList] = React.useState([])
  const [firstUpdate, setFirstUpdate] = React.useState(true)
  const [title, setTitle] = React.useState('')
  const [description, setDescription] = React.useState('')
  const [surveyLoaded, setSurveyLoaded] = React.useState(false)
  const [deletePopupOpen, setDeletePopupOpen] = React.useState(false)
  const [deleteConfirmTitle, setDeleteConfirmTitle] = React.useState('')
   
  if (firstUpdate) {
    setFirstUpdate(false)
    loadSurvey()
  }
  
  async function loadSurvey() {
    const request_body = {
      'login': Cookies.get(Cookies_Basko.LOGIN),
      'session_id': Cookies.get(Cookies_Basko.SESSION_ID)
    }
    
    let questions = await fetch(RequestUrls_Basko.SURVEY + '/' + props.params.id + RequestUrls_Basko.GET_QUESTIONS_WITH_ANSWERS, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    questions = await questions.json()

    let survey = await fetch(RequestUrls_Basko.SURVEY + '/' + props.params.id, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'},
    })
    survey = await survey.json()

    setTitle(survey.title)
    setDescription(survey.description)

    setQuestionsList(questions)
    setSurveyLoaded(true)
  }

  function questionChanged(title, type, value, variants, id) {
    let newQuestions = questionsList.slice()
    newQuestions[id] = {wording: title, type: type, variants: variants, answer: value}
    setQuestionsList(newQuestions)
  }

  function addQuestion() {
    let newQuestions = questionsList.slice()
    newQuestions.push({wording: '', type: 0, answer: ''})
    setQuestionsList(newQuestions)
  }

  function deleteQuestion(index) {
    let newQuestions = questionsList.slice()
    newQuestions[index] = null
    setQuestionsList(newQuestions)
  }

  function saveChangesClick() {
    toast.promise(
      saveQuestions(), {
         loading: Strings_Basko.SAVING,
         success: <b>{Strings_Basko.SURVEY_SAVED}</b>,
         error: <b>{Strings_Basko.OCCURED_ERROR}</b>
       }
    )
  }

  function saveQuestions() {
    return new Promise((resolve, reject) => {
      let questions = []
      questionsList.forEach(question => {
        if (question === null) return
        questions.push({wording: question.wording, type: question.type, variants: question.variants, answer: question.answer})
      })
      
      const request_body = {
        'login': Cookies.get(Cookies_Basko.LOGIN),
        'session_id': Cookies.get(Cookies_Basko.SESSION_ID),
        'title': title,
        'description': description,
        'questions': questions
      }
    
      fetch(RequestUrls_Basko.SURVEY + '/' + props.params.id + RequestUrls_Basko.UPDATE_SURVEY, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        if (response.status === 200) resolve()
        reject()
      }).catch(() => {reject()})
    })
  }

  function onDeleteSurveyClick() {
    if (deleteConfirmTitle === title) {
      toast.promise(
        deleteSurvey(), {
           loading: Strings_Basko.SAVING,
           success: <b>{Strings_Basko.SURVEY_DELETED}</b>,
           error: <b>{Strings_Basko.OCCURED_ERROR}</b>
         }
      )
    } else {
      toast.error(Strings_Basko.INCORRECT_TITLE)
    }
  }

  function deleteSurvey() {
    return new Promise((resolve, reject) => {
      const request_body = {
        'login': Cookies.get(Cookies_Basko.LOGIN),
        'session_id': Cookies.get(Cookies_Basko.SESSION_ID)
      }
    
      fetch(RequestUrls_Basko.SURVEY + '/' + props.params.id + RequestUrls_Basko.DELETE_SURVEY, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        if (response.status === 200) { 
          resolve()
          window.location.replace(PageUrls_Basko.SURVEYS_UNCOMPLETED)
        }
        reject()
      }).catch(() => {reject()})
    })
  }
  
  return (
    <div id='ViewSurveyPage'>
      <Popup open={deletePopupOpen} closeOnDocumentClick onClose={() => {setDeletePopupOpen(false)}}>
        <div className='popupModal'>
          <div className='popupTitle'>
            <a>{Strings_Basko.SURVEY_DELETE_CONFIRM_TITLE_FIRST_PART}</a>
            <a style={{'color': 'red'}}>{Strings_Basko.DELETE}</a>
            <a>{Strings_Basko.SURVEY_DELETE_CONFIRM_TITLE_SECOND_PART}</a>
          </div>
          {Strings_Basko.SURVEY_DELETE_CONFIRM_MESSAGE}

          <TextField
            style={{'width': '30%'}}
            error
            title='Название опроса'
            helperText='Для проверки вы должны ввести название опроса'
            value={deleteConfirmTitle}
            onChange={(e) => {setDeleteConfirmTitle(e.target.value)}} />

          <Button variant='outlined' color='error' onClick={onDeleteSurveyClick}>Удалить опрос</Button>
        </div>
      </Popup>
      
      <div id='questionsList'>
        <TextField
          style={{'width': '30%'}}
          label='Вопрос'
          value={title}
          onChange={(e) => {setTitle(e.target.value)}} />

        <TextField
          style={{'width': '30%'}}
          label='Описание'
          value={description}
          onChange={(e) => {setDescription(e.target.value)}} />

        <Box sx={{ paddingTop: 4 }} />

        {(surveyLoaded ? <>
          {questionsList.map(function(question, index) {
            if (question !== null) question.id = index
            return (question !== null ? (
              <div className='modifyQuestionWrapper'>
                <ModifyQuestion_Basko valueChanged={questionChanged} question={question}/>
                <DeleteIcon onClick={() => {deleteQuestion(index)}} className='deleteQuestionButton' />
              </div>
            ) : (<></>))
          })}
        </> : <></>)}
        <div className='addQuestion' onClick={addQuestion}>
          <AddIcon fontSize='large'/>
        </div>
        <Box sx={{ paddingTop: 4 }} />
      
        <Button variant='outlined' onClick={saveChangesClick}>Сохранить</Button>
        <Box sx={{ paddingTop: 4 }} />
        <Button variant='outlined' onClick={() => {setDeletePopupOpen(true)}} color='error'>Удалить опрос</Button>
        <Box sx={{ paddingTop: 20 }} />
      </div>
    </div>
  )
}