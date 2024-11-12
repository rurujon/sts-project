import React, {useEffect, useState} from 'react';
import axios from 'axios';

const Spring = () => {

	const [data,setData] = useState([])
	
	useEffect(()=>{
		axios.get('/api/hello')
		.then(res=>setData(res.data))
		.catch(error=>console.log(error))
	},[])
	
    return (
        <div>
            <h3>스프링에서 가져온 데이터</h3>
            <ul>
            	<li>{data[0]}</li>
            	<li>{data[1]}</li>
            </ul>
            
            <hr/>
            
            {
            	data.map((text,index)=><p key={index}>
            	{index} / {text}
            	</p>)
            }
        </div>
    );
};

export default Spring;