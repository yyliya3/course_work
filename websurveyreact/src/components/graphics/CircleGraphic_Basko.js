import React from 'react'
import { PieChart, Pie, Tooltip } from 'recharts'

export default function CircleGraphic_Basko(props) {
  return (
    <PieChart width={400} height={300}>
      <Pie
        dataKey='value'
        isAnimationActive={true}
        data={props.data}
        cx='50%'
        cy='50%'
        outerRadius={80}
        fill='#8884d8'
        label />
      <Tooltip separator=' ответило '/>
    </PieChart>
  )
}