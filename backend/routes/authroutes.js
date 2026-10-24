import express from 'express'
import db from './../db.js'
import bcrypt from 'bcrypt'

const router = express.Router()

router.post('/register', (req,res) => {
 
    const {STUid, name , email , password} = req.body
    const hashedpassword = bcrypt.hashSync(password, 8) 
    console.log(req.body);
    try{
      const Selectuserid = db.prepare('SELECT STUid FROM USERS WHERE STUid = ?')
       
      const userid = Selectuserid.get(STUid)
      if(userid === undefined){
       const insertUser = db.prepare('INSERT INTO USERS (username , email , password , STUid) VALUES (?,?,?,?)')
        //console.log("here")
        insertUser.run(name, email, hashedpassword, STUid)
        res.json({message: 'User registered successfully',STUid:userid})

      }
      else{
    
        res.status(400).json({error: 'User already exists'})
      }
    }
    catch(err){
      res.status(500).json({error: 'Internal server error'})
    }
    

})


router.post('/login', (req,res) =>{

    const {STUid , password} = req.body

    try{
         const SelectUser = db.prepare('SELECT STUid , password FROM USERS WHERE STUid = ?')
              
         const user = SelectUser.get(STUid)
        console.log(user)

         if(user!=undefined ){
            if(bcrypt.compareSync(password, user.password)){
                res.status(200).json({message: 'Login successful', STUid: user.STUid})
            }
        
            else{
                res.status(400).json({error: 'wrong password'})
            }
         }
         else{
            res.status(400).json({error: 'Invalid credentials'})
         }
       

    }
    catch(err){
      res.status(500).json({error: 'Internal server error'})
    }
})

export default router