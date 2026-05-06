import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

function Catalogo() {
  const [productos, setProductos] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    axios.get('http://localhost:8080/productos')
      .then(res => setProductos(res.data))
      .catch(err => console.log(err))
  }, [])

  return (
    <div className="min-h-screen bg-white">
      <header className="bg-black text-white py-4 px-8">
        <h1 className="text-2xl font-bold">JTech Store</h1>
      </header>
      <main className="p-8">
        <h2 className="text-xl font-semibold mb-6">Productos</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {productos.map(p => (
            <div key={p.id} onClick={() => navigate('/producto/' + p.id)}
              className="border rounded-xl p-4 cursor-pointer hover:shadow-lg transition">
              <h3 className="font-semibold text-lg">{p.nombre}</h3>
              <p className="text-gray-500">{p.marca}</p>
              <p className="font-bold mt-2">${p.precio}</p>
            </div>
          ))}
        </div>
      </main>
    </div>
  )
}

export default Catalogo