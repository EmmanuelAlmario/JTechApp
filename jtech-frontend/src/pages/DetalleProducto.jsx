import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import axios from 'axios'

function DetalleProducto() {
  const { id } = useParams()
  const [producto, setProducto] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    axios.get('http://localhost:8080/productos/' + id)
      .then(res => setProducto(res.data))
      .catch(err => console.log(err))
  }, [id])

  const agregarAlCarrito = () => {
    const carrito = JSON.parse(localStorage.getItem('carrito') || '[]')
    carrito.push(producto)
    localStorage.setItem('carrito', JSON.stringify(carrito))
    alert('Producto agregado al carrito')
  }

  if (!producto) return <p className="p-8">Cargando...</p>

  return (
    <div className="min-h-screen bg-white">
      <header className="bg-black text-white py-4 px-8 flex justify-between">
        <h1 className="text-2xl font-bold">JTech Store</h1>
        <button onClick={() => navigate('/carrito')}
          className="bg-white text-black px-4 py-2 rounded-lg font-semibold">
          Carrito
        </button>
      </header>
      <main className="p-8 max-w-2xl mx-auto">
        <button onClick={() => navigate('/')} className="text-gray-500 mb-4">Volver</button>
        <h2 className="text-3xl font-bold mb-2">{producto.nombre}</h2>
        <p className="text-gray-500 mb-2">{producto.marca}</p>
        <p className="text-2xl font-bold mb-6">${producto.precio}</p>
        <button onClick={agregarAlCarrito}
          className="bg-black text-white px-6 py-3 rounded-xl font-semibold w-full">
          Agregar al carrito
        </button>
      </main>
    </div>
  )
}

export default DetalleProducto