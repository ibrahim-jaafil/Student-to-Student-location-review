import express from 'express'
import Database from 'better-sqlite3';
import fs from 'fs';
import authroutes from './routes/authroutes.js'
import loadRoutes from './routes/loadRoutes.js'

const app =express()
const PORT = process.env.PORT 
app.use(express.json())



const db = new Database('./data/campus.db');
db.pragma('foreign_keys = ON');
db.exec(fs.readFileSync('schema.sql', 'utf8'));

app.use('/auth', authroutes)
app.use('/load',loadRoutes)

app.listen(PORT, '0.0.0.0',() => {
    console.log(`API running on ${PORT}`)

})