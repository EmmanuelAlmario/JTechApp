import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

function Carrito() {
  const [carrito, setCarrito] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    const items = JSON.parse(localStorage.getItem('carrito') || '[]')
    setCarrito(items)
  }, [])

  const eliminar = (index) => {
    const nuevo = carrito.filter((_, i) => i !== index)
    setCarrito(nuevo)
    localStorage.setItem('carrito', JSON.stringify(nuevo))
  }

  const total = carrito.reduce((acc, p) => acc + p.precio, 0)

  return (
    <div className="min-h-screen bg-white">
      <header className="bg-black text-white py-4 px-8 flex justify-between">
        <h1 className="text-2xl font-bold">JTech Store</h1>
        <button onClick={() => navigate('/')}
          className="bg-white text-black px-4 py-2 rounded-lg font-semibold">
          Volver
        </button>
      </header>
      <main className="p-8 max-w-2xl mx-auto">
        <h2 className="text-2xl font-bold mb-6">Mi Carrito</h2>
        {carrito.length === 0 ? (
          <p className="text-gray-500">El carrito está vacío</p>
        ) : (
          <>
            {carrito.map((p, i) => (
              <div key={i} className="flex justify-between items-center border-b py-4">
                <div>
                  <p className="font-semibold">{p.nombre}</p>
                  <p className="text-gray-500">${p.precio}</p>
                </div>
                <button onClick={() => eliminar(i)}
                  className="text-red-500 font-semibold">
                  Eliminar
                </button>
              </div>
            ))}
            <div className="mt-6 flex justify-between items-center">
              <p className="text-xl font-bold">Total: ${total}</p>
              <button className="bg-black text-white px-6 py-3 rounded-xl font-semibold">
                Comprar
              </button>
            </div>
          </>
        )}
      </main>
    </div>
  )
}

export default Carrito