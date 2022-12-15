import '../App.css'
import React from 'react'
import { MenuItem, TextField } from '@mui/material'
import QuestionTypes_Basko from '../settings/QuestionTypes_Basko'
import { Box } from '@mui/system'
import DeleteIcon from '@mui/icons-material/Delete'
import AddIcon from '@mui/icons-material/Add'
import Radio from '@mui/material/Radio'

export default function ModifyQuestion_Basko(props) {
  const questionTypes = [{
    id: QuestionTypes_Basko.BOOLEAN,
    value: 'Логическое значение'
  }, {
    id: QuestionTypes_Basko.TEXT,
    value: 'Текст'
  }, {
    id: QuestionTypes_Basko.VARIANT,
    value: 'С вариантами ответа'
  }]

  const booleanAnswers = [{
      label: 'Да',
      value: 'true'
    }, {
      label: 'Нет',
      value: 'false'
  }]

  const [variantAnswers, setVariantAnswers] = React.useState(props.question.variants === undefined ? ['Да', 'Нет', 'Не знаю'] : props.question.variants)

  const [title, setTitle] = React.useState(props.question.wording)
  const [value, setValue] = React.useState(props.question.answer)
  const [type, setType] = React.useState(props.question.type)

  function valueChanged(e) {
    setValue(e.target.value)
    props.valueChanged(title, type, e.target.value, variantAnswers, props.question.id)
  }

  function titleChanged(e) {
    setTitle(e.target.value)
    props.valueChanged(e.target.value, type, value, variantAnswers, props.question.id)
  }

  function typeChanged(e) {
    setType(e.target.value)
    
    let newValue = ''

    switch(e.target.value) {
      case QuestionTypes_Basko.TEXT:
        newValue = ''
        break
      
      case QuestionTypes_Basko.BOOLEAN:
        newValue = booleanAnswers[0].value
        break
        
      case QuestionTypes_Basko.VARIANT:
        newValue = variantAnswers[0]
        break
    }

    setValue(newValue)
    props.valueChanged(title, e.target.value, newValue, variantAnswers, props.question.id)
  }

  function renderSwitch() {
    switch(type) {
      case QuestionTypes_Basko.TEXT:
        return (
          <>
            <TextField
              label='Правильный ответ'
              value={value}
              onChange={(e) => {valueChanged(e)}} />
          </>
        )

      case QuestionTypes_Basko.BOOLEAN:
        return (
          <>
            <TextField
              margin='dense'
              select
              required
              label=''
              value={value}
              onChange={(e) => {valueChanged(e)}}
              variant='standard'>
              {booleanAnswers.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </TextField>
          </>
        )

      case QuestionTypes_Basko.VARIANT:
        function addClick() {
          let newVariantAnswers = variantAnswers.slice()
          newVariantAnswers.push('')
          setVariantAnswers(newVariantAnswers)
        }

        return (
          <div className='answerVarintWrapper'>
            {variantAnswers.map((item, index) => {
              function onVariantChange(value, index) {
                let newVariantAnswers = variantAnswers.slice()
                newVariantAnswers[index] = value
                setVariantAnswers(newVariantAnswers)

                props.valueChanged(title, type, value, newVariantAnswers, props.question.id)
              }
              
              function onDelete() {
                let newVariantAnswers = variantAnswers.slice()
                newVariantAnswers.splice(index, 1)
                setVariantAnswers(newVariantAnswers)

                props.valueChanged(title, type, value, newVariantAnswers, props.question.id)
              } 

              return (
                <div className='answerVarint'>
                  <Radio
                    checked={value === item}
                    onChange={valueChanged}
                    value={item} />

                  <TextField
                    label=''
                    onChange={(e) => {onVariantChange(e.target.value, index)}}
                    value={item} />
                  <DeleteIcon onClick={onDelete} className='answerVarintDelete'/>
                </div>
              )
            })}
            <div className='addAnswerVarint' onClick={addClick} >
              <AddIcon />
            </div>
          </div>
        )

      default:
        return (<></>);
    }
  }
  
  return (
    <div className='modifyQuestion'>
      <Box sx={{ paddingTop: 2 }} />

      <TextField
        label='Вопрос'
        value={title}
        onChange={(e) => {titleChanged(e)}} />

      <TextField
        margin='dense'
        select
        required
        label=''
        value={type}
        variant='standard'
        onChange={(e) => {typeChanged(e)}} >
        {questionTypes.map((item) => (
          <MenuItem key={item.id} value={item.id}>
            {item.value}
          </MenuItem>
        ))}
      </TextField>
      
      {renderSwitch()}
    </div>
  )
}