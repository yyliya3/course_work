import '../App.css'
import React from 'react'
import FormControl from '@mui/material/FormControl'
import TextField from '@mui/material/TextField'
import Strings_Basko from '../settings/Strings_Basko'
import RequestUrls_Basko from '../settings/RequestUrls_Basko'
import { containsCharacters } from '../utils/StringUtils_Basko'

export class LoginFieldTypes {
  static REGISTER = 0
  static LOGIN = 1
}

export default class LoginField_Basko extends React.Component {  
  avalibleCharacters = '1234567890AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz_-'

  constructor(props) {
    super(props)

    this.state = {
      login: '',
      error: '',
      correct: false
    }

    this.setError = this.setError.bind(this);
    this.onChange = this.onChange.bind(this);
    this.validate = this.validate.bind(this);
    this.onFocus = this.onFocus.bind(this);
    this.checkCorrect = this.checkCorrect.bind(this);

    this.validate(this.state.login);
    this.checkCorrect(this.state.login);
  }

  setError = (errorText) => {
    this.state.error = errorText
    this.setState({...this.state, error: errorText})
  }

  onChange = async (event) => {
    await this.setState({...this.state, login: event.target.value})
    this.validate(event.target.value).then((needChangeValue) => {
      if (needChangeValue) {
        this.checkCorrect(event.target.value)
      } else {
        this.setState(state => ({...state, login: state.login.slice(0, -1)}))
      }
    })
  }

  onFocus = () => {
    this.validate(this.state.login)
    this.checkCorrect(this.state.login)
  }

  checkCorrect = (login) => {
    if (login === '') {
      this.setState({...this.state, correct: false})
      return
    }

    this.setState({...this.state, correct: true})
  }

  validate = async (login) => {
    this.setError('')
    if (login === '') {
      this.setError(Strings_Basko.LOGIN_EMPTY)
      return true
    }

    if (containsCharacters(login, this.avalibleCharacters)) {
      this.setError(Strings_Basko.NOT_AVALIBLE_CHARACTERS + '\"' + this.avalibleCharacters + '\"')
      return false
    }
    
    let isLoginExist = await fetch(RequestUrls_Basko.LOGIN_EXIST + '/' + login, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'}
    })
    isLoginExist = await isLoginExist.json()

    switch(this.props.type) {
      case LoginFieldTypes.REGISTER:
        if (isLoginExist) this.setError(Strings_Basko.LOGIN_EXIST)
        else this.setError(''); 
        break

      case LoginFieldTypes.LOGIN:
        if (!isLoginExist) this.setError(Strings_Basko.LOGIN_NOT_EXIST)
        else this.setError(''); 
        break
    }
    
    return true;
  }

  render() {
    return (
      <FormControl className='formField' variant='standard' margin='dense'>
        <TextField id='login' label='Логин' variant='standard' margin='dense'
                required
                value={this.state.login}
                error={this.state.error !== ''}
                helperText={this.state.error}
                onChange={(e) => this.onChange(e)} 
                onFocus={this.onFocus} 
                onBlur={this.onFocus} />
      </FormControl>
    )
  }
}