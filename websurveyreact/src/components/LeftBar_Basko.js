import '../App.css'
import React from 'react'
import List from '@mui/material/List' 
import ListItem from '@mui/material/ListItem' 
import ListItemIcon from '@mui/material/ListItemIcon' 
import ListItemText from '@mui/material/ListItemText'
import AutoAwesomeMosaicIcon from '@mui/icons-material/AutoAwesomeMosaic'
import AddModeratorIcon from '@mui/icons-material/AddModerator'
import { useNavigate } from 'react-router-dom'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import GavelIcon from '@mui/icons-material/Gavel'

let currentIndex = 0

export default function LeftBar_Basko() {
  const navigate = useNavigate()

  function onClick(index, href) {
    if (index === currentIndex) return
    currentIndex = index
    navigate(href)
  }
  
  return (
    <div id='leftBar'>
      <List> 
        {[{text: 'Непройденые опросы', icon: <AutoAwesomeMosaicIcon />, href: PageUrls_Basko.SURVEYS_UNCOMPLETED},
          {text: 'Мои опросы', icon: <AddModeratorIcon />, href: PageUrls_Basko.SURVEYS_MY},
          {text: 'Блокировка', icon: <GavelIcon />, href: PageUrls_Basko.BAN}
          ].map((item, index) => (
          <ListItem button onClick={() => {onClick(index, item.href)}} key={item.text} selected={index === currentIndex}>
            <ListItemIcon>{item.icon}</ListItemIcon> 
            <ListItemText primary={item.text} />
          </ListItem>
        ))}
      </List>
    </div>
  )
}