import '../App.css'
import React from 'react'
import Particles from 'react-tsparticles'

export default class Particles_Basko extends React.Component {
  constructor(props) {
    super(props)
  }

  particleOptions = {
    fpsLimit: 60,
    fullScreen: { enable: false },
    particles: {
      number: {
        value: 60,
        limit: 100,
        density: {
          enable: true,
          value_area: 800
        }
      },
      color: {
        value: this.props.color
      },
      opacity: {
        value: 0.5,
        random: true,
        anim: {
          enable: true,
          speed: 1,
          opacity_min: 0.5,
          sync: false
        }
      },
      line_linked: {
        enable: true,
        distance: 100,
        color: this.props.color,
        opacity: 1,
        width: 1
      },
      move: {
        enable: true,
        speed: 0.2,
        direction: this.props.direction,
        random: false,
        straight: false,
        out_mode: 'out',
        bounce: false,
        attract: {
          enable: false,
          rotateX: 600,
          rotateY: 1200
        }
      }
    }
  }

  render() {
    return (
      <Particles className='particles' options={this.particleOptions} />
    )
  }
}