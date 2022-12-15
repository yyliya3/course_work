import './App.css'
import React, { useRef, useEffect, useState } from 'react'
import RegisterPage_Basko from './pages/RegisterPage_Basko'
import RegisterSuccessPage_Basko from './pages/RegisterSuccessPage_Basko'
import LoginPage_Basko from './pages/LoginPage_Basko'
import { BrowserRouter, Route, Routes, Navigate, useLocation } from 'react-router-dom'
import PageUrls_Basko from "./settings/PageUrls_Basko"
import HomePage_Basko from './pages/HomePage_Basko'
import MySurveysPage_Basko from './pages/MySurveysPage_Basko'
import LeftBar_Basko from './components/LeftBar_Basko'
import Cookies_Basko from './settings/Cookies_Basko'
import RequestUrls_Basko from './settings/RequestUrls_Basko'
import Cookies from 'js-cookie'
import TopBar_Basko from './components/TopBar_Basko'
import UncompletedSurveysPage_Basko from './pages/UncompletedSurveysPage_Basko'
import ViewSurveyPage_Basko from './pages/ViewSurveyPage_Basko'
import { useParams } from 'react-router-dom'
import { Toaster } from 'react-hot-toast'
import ModifySurveyPage_Basko from './pages/ModfiySurveyPage_Basko'
import RecoveryPage_Basko from './pages/RecoveryPage_Basko'
import StatisticsSurveyPage_Basko from './pages/StatisticsSurveyPage_Basko'
import BanPage_Basko from './pages/BanPage_Basko'

function Root() {
  const topBar = useRef(null)
  const location = useLocation()

  async function verifySession() {
    let login = Cookies.get(Cookies_Basko.LOGIN)
    let sessionId = Cookies.get(Cookies_Basko.SESSION_ID)
    
    let request_body = {
      'login': login,
      'session_id': sessionId
    }
  
    let sessionIsGood = false
    if (login != null && sessionId != null) {
      sessionIsGood = await fetch(RequestUrls_Basko.VERIFY_SESSION, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      })
      sessionIsGood = await sessionIsGood.json()
    }

    Cookies.set(Cookies_Basko.AUTHORIZED, sessionIsGood)
  }

  const ParamsWrapper = (props) => {
    const params = useParams();
    return <props.element {...{...props, params: params} } />
  }
  
  verifySession()

  return (
    <>
      <div> <Toaster position='bottom-center' reverseOrder={false} /> </div>
      <Routes> 
        <Route path={PageUrls_Basko.SURVEYS_UNCOMPLETED} element={<UncompletedSurveysPage_Basko />} />
        <Route path={PageUrls_Basko.SURVEYS_MY} element={<MySurveysPage_Basko />} />
        
        <Route path={PageUrls_Basko.BAN} element={<BanPage_Basko />} />
        
        <Route path={PageUrls_Basko.RECOVERY} element={<RecoveryPage_Basko />} />

        <Route path={PageUrls_Basko.SURVEYS + '/*'} element={<Navigate to={PageUrls_Basko.SURVEYS_UNCOMPLETED} replace />} />
        
        <Route path={PageUrls_Basko.SURVEY + '/:id'} element={<ParamsWrapper element={ViewSurveyPage_Basko} />} />
        <Route path={PageUrls_Basko.SURVEY + '/:id' + PageUrls_Basko.MODIFY} element={<ParamsWrapper element={ModifySurveyPage_Basko} />} />
        <Route path={PageUrls_Basko.SURVEY + '/:id' + PageUrls_Basko.STATISTICS} element={<ParamsWrapper element={StatisticsSurveyPage_Basko} />} />

        <Route path={PageUrls_Basko.REGISTER} element={<RegisterPage_Basko />} />
        <Route path={PageUrls_Basko.REGISTER_SUCCESS} element={<RegisterSuccessPage_Basko />} />
        <Route path={PageUrls_Basko.LOGIN} element={<LoginPage_Basko />} />
        <Route path={PageUrls_Basko.HOME} element={<HomePage_Basko />} />
      </Routes>
      
      <Routes>
        <Route path={PageUrls_Basko.SURVEYS + '/*'} element={<TopBar_Basko doNotShowSurveysButton/>} />
        <Route path='/*' element={<TopBar_Basko />} />
      </Routes>
      
      <Routes>
        <Route path={PageUrls_Basko.SURVEYS + '/*'} element={<LeftBar_Basko />} />
      </Routes>
    </>
  )
}

export default function App() {  
  return (
    <BrowserRouter>
      <Root />
    </BrowserRouter>
  )
}