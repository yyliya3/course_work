import '../App.css'
import React, { useRef } from 'react'
import Helmet from 'react-helmet'
import Particles_Basko from '../components/Particles_Basko'
import LoginField_Basko, { LoginFieldTypes } from '../components/LoginField_Basko'
import PasswordField_Basko from '../components/PasswordField_Basko'
import FormControl from '@mui/material/FormControl'
import FormHelperText from '@mui/material/FormHelperText'
import Strings_Basko from '../settings/Strings_Basko'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import Button from '@mui/material/Button'
import Cookies from 'js-cookie'
import RequestUrls_Basko from '../settings/RequestUrls_Basko'
import Cookies_Basko from '../settings/Cookies_Basko'
import Box from '@mui/material/Box'

export default function RecoveryPage_Basko() {
  const [resultText, setResultText] = React.useState(false)

  const loginField = useRef(null)
  const passwordField = useRef(null)
  const retypePasswordField = useRef(null)
  const codePhraseField = useRef(null)

  async function validate() {
    if (!loginField.current.state.correct) return false
    if (!passwordField.current.state.correct) return false
    if (!retypePasswordField.current.state.correct) return false
    if (!codePhraseField.current.state.correct) return false
    return true
  }

  async function onLoginClick() {
    if (!await validate()) return

    if (passwordField.current.state.password === retypePasswordField.current.state.password) {
      const request_body = {
        'login': loginField.current.state.login,
        'password': passwordField.current.state.password,
        'code_phrase': codePhraseField.current.state.password
      };

      let sessionId = await fetch(RequestUrls_Basko.RECOVERY, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      })

      if (sessionId.status !== 200) {
        setResultText(Strings_Basko.INCORRECT_KEY_PHRASE)
        return;
      }

      Cookies.set(Cookies_Basko.LOGIN, loginField.current.state.login)
      Cookies.set(Cookies_Basko.SESSION_ID, (await sessionId.json()))
      Cookies.set(Cookies_Basko.AUTHORIZED, 'true')
      window.location.assign(PageUrls_Basko.HOME)
    }
  }

  return (
    <div id='RecoveryPage'>
      <Helmet title='Восстановление' />

      <Particles_Basko color='#000000' direction='none' />
      <div className='registerContainer'>
        <div style={{padding: '20px', widtgih: '70%', margin: '20px auto'}}>
          <form noValidate autoComplete='off'>
            <LoginField_Basko ref={loginField} type={LoginFieldTypes.LOGIN}/>
            <PasswordField_Basko ref={codePhraseField} title='Кодовая фраза' required/>
            <PasswordField_Basko ref={passwordField} title='Пароль' required />
            <PasswordField_Basko ref={retypePasswordField} title='Повторите пароль' required />

            <Box sx={{ paddingTop: 2 }} />
            <FormControl className='formField' variant='standard' margin='dense'>
              <Button variant='contained' onClick={() => {onLoginClick()}}>Войти</Button>
              <FormHelperText error={resultText !== ''}>{resultText}</FormHelperText>
            </FormControl>
          </form>
        </div>

        {Strings_Basko.ALSO_YOU_CAN_REGISTER_FIRST_PART}
        <a href={PageUrls_Basko.REGISTER}>{Strings_Basko.ALSO_YOU_CAN_REGISTER_SECOND_PART}</a>
      </div>
    </div>
  )
}