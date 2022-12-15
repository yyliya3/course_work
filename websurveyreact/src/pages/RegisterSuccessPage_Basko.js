import '../App.css'
import React from 'react'
import Strings_Basko from '../settings/Strings_Basko'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import { Container } from '@mui/material'
import Helmet from 'react-helmet'
import Box from '@mui/material/Box'

export default function RegisterSuccessPage_Basko () {
  return(
    <div id='RegisterSuccessPage'>
      <Helmet title='Регистрация выполнена успешно!' />
      
      <Box sx={{ paddingTop: 20 }} />
      <div>
        <a className='title'>
          {Strings_Basko.REGISTER_SUCCESS}
        </a>
      </div>
      <Box sx={{ paddingTop: 4 }} />
      <div>
        <a className='subtitle'>
          <a href={PageUrls_Basko.LOGIN}>{Strings_Basko.NOW_YOU_CAN_LOGIN}</a>
        </a>
      </div>
    </div>
  )
}