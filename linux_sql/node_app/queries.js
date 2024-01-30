const Pool = require('pg').Pool
const pool = new Pool({
  user: 'postgres',
  host: 'postgres',
  database: 'host_agent',
  password: 'password',
  port: 5432,
})

const getUsage = (request, response) => {
    pool.query('SELECT * FROM host_usage', (error, results) => {
      if (error) {
        throw error
      }
      response.status(200).json(results.rows)
    })
  }
  

  module.exports ={
    getUsage
  }
