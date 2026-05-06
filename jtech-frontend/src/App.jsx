import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Catalogo from './pages/Catalogo'
import DetalleProducto from './pages/DetalleProducto'
import Carrito from './pages/Carrito'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Catalogo />} />
        <Route path="/producto/:id" element={<DetalleProducto />} />
        <Route path="/carrito" element={<Carrito />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App