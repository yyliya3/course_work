import '../App.css'
import React from 'react'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import AssessmentIcon from '@mui/icons-material/Assessment'
import IconButton from '@mui/material/IconButton'

export default function SurveyCard_Basko(props) {
  function onClick() {
    window.location.assign(PageUrls_Basko.SURVEY + '/' + props.survey['id'] + (props.type === 'my' ? PageUrls_Basko.MODIFY : ''))
  }

  function onStatisticsClick() {
    window.location.assign(PageUrls_Basko.SURVEY + '/' + props.survey['id'] + PageUrls_Basko.STATISTICS)
  }

  return (
    <div className={(props.type === 'my' ?  'mySurveyCard' : 'surveyCard')}>
      <div className='cardClickArea' onClick={onClick} />
      <div className='cardTitle'>{props.survey['title']}</div>
      <div className='cardDescription'>{props.survey['description']}</div>
      {(props.type !== 'my') ? (
        <div className='cardAuthor'>{props.survey['author']}</div>
      ) : (
        <div className='cardMenus'>
          <div className='cardStatistics'>
            <IconButton onClick={onStatisticsClick} component='span'>
              <AssessmentIcon />
            </IconButton>
          </div>
        </div>
      )}
    </div>
  )
}