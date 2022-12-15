import '../App.css'
import React, { useRef } from 'react'
import ReactDOM from 'react-dom'
import RequestUrls_Basko from '../settings/RequestUrls_Basko'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import Cookies_Basko from '../settings/Cookies_Basko'
import Button from '@mui/material/Button'
import Cookies from 'js-cookie'

export default class TopBar_Basko extends React.Component {
  constructor(props) {
    super(props)

    this.onLogOutClick = this.onLogOutClick.bind(this)
  }

  onLogOutClick = () => {
    let request_body = {
      'login': Cookies.get(Cookies_Basko.LOGIN),
      'session_id': Cookies.get(Cookies_Basko.SESSION_ID)
    }

    fetch(RequestUrls_Basko.REMOVE_SESSION, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })

    Cookies.remove(Cookies_Basko.LOGIN)
    Cookies.remove(Cookies_Basko.SESSION_ID)
    Cookies.remove(Cookies_Basko.AUTHORIZED)
    window.location.replace(PageUrls_Basko.HOME)
  }

  SurveysButton = (props) => {
    if (props.doNotShowSurveysButton === true) return null
    
    return (
      <Button color='inherit' href={PageUrls_Basko.SURVEYS}>Опросы</Button>
    )
  }

  componentDidMount = () => {
    ReactDOM.render(
      Cookies.get(Cookies_Basko.AUTHORIZED) === 'true' ? this.loggedInComponent : this.notLoggedInComponent,
      document.getElementById('topBar')
    )
  }

  loggedInComponent = (
    <div className='topBar'>
      <this.SurveysButton doNotShowSurveysButton={this.props.doNotShowSurveysButton} />
      <Button color='inherit'>{Cookies.get(Cookies_Basko.LOGIN)}</Button>
      <Button color='inherit' onClick={() => {this.onLogOutClick()}}>Выйти</Button>
    </div>
  )

  notLoggedInComponent = (
    <div className='topBar'>
      <Button color='inherit' href={PageUrls_Basko.LOGIN}>Войти</Button>
      <Button color='inherit' href={PageUrls_Basko.REGISTER}>Зарегистрироваться</Button>
    </div>
  )

  render() {
    return (
      <div id='topBar'>

      </div>
    )
  }
}