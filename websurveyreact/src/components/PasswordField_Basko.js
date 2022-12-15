import '../App.css'
import React from 'react'
import FormControl from '@mui/material/FormControl'
import InputLabel from '@mui/material/InputLabel'
import Input from '@mui/material/Input'
import InputAdornment from '@mui/material/InputAdornment'
import IconButton from '@mui/material/IconButton'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import FormHelperText from '@mui/material/FormHelperText'
import Strings_Basko from '../settings/Strings_Basko'
import { containsCharacters } from '../utils/StringUtils_Basko'

export default class PasswordField_Basko extends React.Component {
  avalibleCharacters = '1234567890AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz_-*~'

  constructor(props) {
    super(props)

    this.state = {
      amount: '', 
      password: '',
      weight: '',
      weightRange: '',
      showPassword: false,
      error: '',
      correct: false
    }

    this.handleChange = this.handleChange.bind(this);
    this.handleClickShow = this.handleClickShow.bind(this);
    this.handleMouseDown = this.handleMouseDown.bind(this);
    this.validate = this.validate.bind(this);
    this.checkCorrect = this.checkCorrect.bind(this);
    this.onFocus = this.onFocus.bind(this);
    this.setError = this.setError.bind(this);

    this.validate(this.state.password);
    this.checkCorrect(this.state.password);
  }

  validate = (password) => {
    this.setError('')

    if (password === '') {
      this.setError(Strings_Basko.FIELD_EMPTY)
      return true
    }

    if (containsCharacters(password, this.avalibleCharacters)) {
      this.setError(Strings_Basko.NOT_AVALIBLE_CHARACTERS + '\"' + this.avalibleCharacters + '\"')
      return false;
    }

    return true
  }

  setError = (errorText) => {
    this.state.error = errorText
    this.setState({...this.state, error: errorText})
  }

  checkCorrect = (password) => {
    if (password === '') {
      this.setState({...this.state, correct: false})
      return
    }

    this.setState({...this.state, correct: true})
  }

  onFocus = () => {  
    this.validate(this.state.password)
    this.checkCorrect(this.state.password)
  }

  handleChange = (prop) => (event) => {  
    let newPassword = event.target.value
    if (!this.validate(newPassword)) return
    this.checkCorrect(newPassword)
    
    this.setState({...this.state, [prop]: newPassword})
    if (this.props.onChange != null) this.props.onChange(null)
  }
  
  handleClickShow = () => {
    this.setState(prevState => ({
      showPassword: !prevState.showPassword
    }));
  }

  handleMouseDown = (event) => {
    event.preventDefault()
  }

  render() {
    return (
      <FormControl className='formField' variant='standard' margin='dense'>
        <InputLabel htmlFor='password' error={this.state.error !== ""} >{this.props.title}</InputLabel>
        <Input
          type={this.state.showPassword ? 'text' : 'password'}
          value={this.state.password}
          error={this.state.error !== ""}
          onChange={this.handleChange('password')}
          onFocus={this.onFocus}
          onBlur={this.onFocus}
          endAdornment={
            <InputAdornment position='end'>
              <IconButton
                aria-label='toggle password visibility'
                onClick={this.handleClickShow}
                onMouseDown={this.handleMouseDown}
              >
                {this.state.showPassword ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>
          }
        />
        <FormHelperText error={this.state.error !== ""}>{this.state.error}</FormHelperText>
      </FormControl>
    )
  }
}