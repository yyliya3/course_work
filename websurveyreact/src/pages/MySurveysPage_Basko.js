import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Basko from '../settings/Cookies_Basko'
import RequestUrls_Basko from '../settings/RequestUrls_Basko'
import SurveyCard_Basko from '../components/SurveyCard_Basko'
import Popup from 'reactjs-popup'
import AddIcon from '@mui/icons-material/Add'
import CloseIcon from '@mui/icons-material/Close'
import { Button, TextField } from '@mui/material'
import toast from 'react-hot-toast'
import Strings_Basko from '../settings/Strings_Basko'

export default function MySurveysPage_Basko() {
  const [mySurveys, setMySurveys] = React.useState([])
  const [firstUpdate, setFirstUpdate] = React.useState(true)
  const [popupOpen, setPopupOpen] = React.useState(false)
  const [createSurveyTitle, setCreateSurveyTitle] = React.useState('')
  const [createSurveyDescription, setCreateSurveyDescription] = React.useState('')
  
  if (firstUpdate) {
    setFirstUpdate(false)
    loadSurveys()
  }

  async function loadSurveys() {
    const request_body = {
      'login': Cookies.get(Cookies_Basko.LOGIN),
      'session_id': Cookies.get(Cookies_Basko.SESSION_ID)
    }
  
    let surveys = await fetch(RequestUrls_Basko.GET_MY_SURVEYS, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    surveys = await surveys.json()

    setMySurveys(surveys)
  }

  function onOpenPopupClick() {
    setPopupOpen(true)
  }

  function onCreateSurveyClick() {
    toast.promise(
      createSurvey(), {
         loading: Strings_Basko.SAVING,
         success: <b>{Strings_Basko.SURVEY_CREATED}</b>,
         error: <b>{Strings_Basko.OCCURED_ERROR}</b>
       }
    )
  }

  function createSurvey() {
    return new Promise((resolve, reject) => {
      const request_body = {
        'login': Cookies.get(Cookies_Basko.LOGIN),
        'session_id': Cookies.get(Cookies_Basko.SESSION_ID),
        "title": createSurveyTitle,
        "description": createSurveyDescription
      }

      fetch(RequestUrls_Basko.CREATE_SURVEY, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        if (response.status === 200) {
          window.location.reload()
          resolve()
        }
        reject()
      }).catch(() => {reject()})
    })
  }
  
  return (
    <div id='SurveysPage'>
      <Popup open={popupOpen} closeOnDocumentClick onClose={() => {setPopupOpen(false)}}>
        <div className='popupModal'>
          <a className='closePopup' onClick={() => {setPopupOpen(false)}}>
            <CloseIcon />
          </a>

          <TextField
            style={{'width': '60%'}}
            label='Название опроса'
            value={createSurveyTitle}
            onChange={(e) => {setCreateSurveyTitle(e.target.value)}} />

          <TextField
            style={{'width': '60%'}}
            label='Описание'
            value={createSurveyDescription}
            onChange={(e) => {setCreateSurveyDescription(e.target.value)}} />

          <Button variant='outlined' onClick={onCreateSurveyClick}>Создать опрос</Button>
        </div>
      </Popup>

      <div id='surveysList'>
        {mySurveys.map(function(survey) {
          return(
            <SurveyCard_Basko survey={survey} type='my'/>
          )
        })}
        <div className='createSurveyCard' onClick={onOpenPopupClick}>
          <AddIcon />
        </div>
      </div>
    </div>
  )
}