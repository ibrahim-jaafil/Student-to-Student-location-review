import express from 'express'
import db from './../db.js'

const router = express.Router()



router.get("/loadPlaces",(req,res)=>{
    const {cid,name}=req.body
    
    try{
    const getPlace = db.prepare("SELECT * FROM PLACE WHERE cid=?")
    const  places = getPlace.all(cid)
    if(cid!=undefined){
    console.log(places)
    res.status(200).json({places})
    }
    else{
        res.status(300).json({message:"places not found for this campus"})
    }
    }
    catch{
        res.status(500).json({message:"internal server error"})
    }



})


export default router;