import '../App.css'
import React from 'react'
import Helmet from 'react-helmet'
import Button from '@mui/material/Button'
import Strings_Basko from '../settings/Strings_Basko'
import Particles_Basko from '../components/Particles_Basko'
import PageUrls_Basko from '../settings/PageUrls_Basko'
import Cookies_Basko from '../settings/Cookies_Basko'
import Cookies from 'js-cookie'
import Box from '@mui/material/Box'
import Typewriter from 'typewriter-effect'

export default function HomePage_Basko() { 
  function onTryNowButton() {
    window.location.replace(Cookies.get(Cookies_Basko.AUTHORIZED) === 'true' ? PageUrls_Basko.SURVEYS : PageUrls_Basko.REGISTER)
  }
  
  return (
    <div id='HomePage'>
      <Helmet title='Веб опросы' />

      <div id='homePageMainPart'>  
        <Particles_Basko color='#000000' direction='none' />
        
        <Box sx={{ paddingTop: 24 }} />
        
        <div> 
          <Typewriter
            options={{
              strings: [Strings_Basko.HOME_PAGE_TITLE],
              autoStart: true,
              delay: 50, 
              deleteSpeed: Infinity,
              wrapperClassName: 'title',
              cursorClassName: 'Typewriter__cursor title'
            }}
          />  
        </div>
  
        <div> 
          <a className='subtitle'>{Strings_Basko.HOME_PAGE_SUBTITLE}</a> 
        </div>
        
        <Box sx={{ paddingTop: 4 }} />
        <Button variant="outlined" color='inherit' onClick={() => {onTryNowButton()}}>{Strings_Basko.TRY_NOW}</Button>
      </div>

      <div id='homePageSecondPart'> 
        
      </div>
    </div>
  )
}
  