import express from 'express'
import db from './../db.js'

const router = express.Router()

router.get("/loadCampuses",(req,res)=>{

    try{
    const getCampuses = db.prepare("SELECT * FROM CAMPUS")
    const Campuses = getCampuses.all()

        res.status(200).json(Campuses)
    }
    catch{
         res.status(500).json({message:"internal server error"})

    }



})

router.post("/loadPlaces",(req,res)=>{
    const {cid}=req.body
    
    try{
    const getPlace = db.prepare("SELECT * FROM PLACE WHERE cid=?")
    const  places = getPlace.all(cid)
    if(cid!=undefined){
    console.log(places)
    res.status(200).json(places)
    }
    else{
        res.status(300).json({message:"places not found for this campus"})
    }
    }
    catch{
        res.status(500).json({message:"internal server error"})
    }



})

router.post("/loadReviews",(req,res)=>{
    const {pid} = req.body

    try{
        const getReviews = db.prepare("SELECT * FROM REVIEW WHERE pid=?")
        const reviews=getReviews.all(pid)
        res.status(200).json(reviews)

    }
    catch{
        res.status(500).json({"message":"internal server erro"})
    }
})

router.get("/loadUsers",(req,res)=>{
        try{
        const getUsers = db.prepare("SELECT STUid , username , email FROM USERS")
        const users=getUsers.all()
       // console.log(users)
        res.status(200).json(users)

    }
    catch{
        res.status(500).json({"message":"internal server erro"})
    }
})

router.post("/loadfavs",(req,res)=>{
    const {STUid}=req.body

    try{

         const getfavs = db.prepare("SELECT pid FROM FAVORITES WHERE STUid=? ")  
         const favs = getfavs.all(STUid);
         res.status(200).json(favs)
    }
    catch{
        res.status(500).json({"message":"internal server error"})

    }
})



export default router;