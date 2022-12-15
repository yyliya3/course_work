import React from 'react'
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts'

export default function BarGraphic_Basko(props) {
  return (
    <BarChart
      width={500}
      height={300}
      data={props.data}
      margin={{
        top: 5,
        right: 30,
        left: 20,
        bottom: 5,
      }}>
      <CartesianGrid strokeDasharray='3 3' />
      <XAxis dataKey='name' />
      <YAxis allowDecimals={false} />
      <Tooltip />
      <Legend />
      <Bar dataKey='value' name='Количество ответивших' fill='#8884d8' />
    </BarChart>
  )
}