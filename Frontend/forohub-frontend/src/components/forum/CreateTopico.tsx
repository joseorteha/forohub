import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { topicoService } from '../../services/topicos';
import { useAuth } from '../../context/AuthContext';
import { Plus, ArrowLeft } from 'lucide-react';
import type { TopicoRequest } from '../../types';

// Cursos hardcodeados por ahora
const CURSOS = [
  { id: 1, nombre: 'Spring Boot', categoria: 'Backend' },
  { id: 2, nombre: 'React', categoria: 'Frontend' },
  { id: 3, nombre: 'Java Fundamentals', categoria: 'Backend' },
  { id: 4, nombre: 'JavaScript', categoria: 'Frontend' },
  { id: 5, nombre: 'PostgreSQL', categoria: 'Base de Datos' },
];

export const CreateTopico: React.FC = () => {
  const [formData, setFormData] = useState({
    titulo: '',
    mensaje: '',
    cursoId: ''
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;

    setIsLoading(true);
    setError('');

    try {
      const topicoRequest: TopicoRequest = {
        titulo: formData.titulo,
        mensaje: formData.mensaje,
        autorId: parseInt(user.id.toString()),
        cursoId: parseInt(formData.cursoId)
      };

      await topicoService.createTopico(topicoRequest);
      navigate('/');
    } catch (err: any) {
      setError(err.message || 'Error al crear el tópico');
    } finally {
      setIsLoading(false);
    }
  };

  const isFormValid = formData.titulo.trim() && formData.mensaje.trim() && formData.cursoId;

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-3xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-6">
          <button
            onClick={() => navigate('/')}
            className="flex items-center space-x-2 text-gray-600 hover:text-gray-900 mb-4"
          >
            <ArrowLeft className="w-4 h-4" />
            <span>Volver al inicio</span>
          </button>
          
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 bg-primary-100 rounded-lg flex items-center justify-center">
              <Plus className="w-5 h-5 text-primary-600" />
            </div>
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Crear Nuevo Tópico</h1>
              <p className="text-gray-600">Comparte una pregunta o inicia una discusión</p>
            </div>
          </div>
        </div>

        {/* Form */}
        <div className="card">
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label htmlFor="titulo" className="block text-sm font-medium text-gray-700 mb-2">
                Título del tópico *
              </label>
              <input
                id="titulo"
                name="titulo"
                type="text"
                value={formData.titulo}
                onChange={handleChange}
                required
                className="input-field"
                placeholder="Describe tu pregunta o problema de forma clara..."
                maxLength={100}
              />
              <p className="text-xs text-gray-500 mt-1">
                {formData.titulo.length}/100 caracteres
              </p>
            </div>

            <div>
              <label htmlFor="cursoId" className="block text-sm font-medium text-gray-700 mb-2">
                Curso *
              </label>
              <select
                id="cursoId"
                name="cursoId"
                value={formData.cursoId}
                onChange={handleChange}
                required
                className="input-field"
              >
                <option value="">Selecciona un curso</option>
                {CURSOS.map(curso => (
                  <option key={curso.id} value={curso.id}>
                    {curso.nombre} - {curso.categoria}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label htmlFor="mensaje" className="block text-sm font-medium text-gray-700 mb-2">
                Descripción detallada *
              </label>
              <textarea
                id="mensaje"
                name="mensaje"
                value={formData.mensaje}
                onChange={handleChange}
                required
                rows={8}
                className="input-field resize-none"
                placeholder="Explica tu pregunta con el mayor detalle posible. Incluye código si es necesario, pasos que has intentado, mensajes de error, etc."
                maxLength={2000}
              />
              <p className="text-xs text-gray-500 mt-1">
                {formData.mensaje.length}/2000 caracteres
              </p>
            </div>

            {error && (
              <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                {error}
              </div>
            )}

            <div className="flex justify-end space-x-3 pt-4 border-t border-gray-200">
              <button
                type="button"
                onClick={() => navigate('/')}
                className="btn-secondary"
              >
                Cancelar
              </button>
              <button
                type="submit"
                disabled={!isFormValid || isLoading}
                className="btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {isLoading ? 'Creando...' : 'Crear Tópico'}
              </button>
            </div>
          </form>
        </div>

        {/* Tips */}
        <div className="mt-6 bg-blue-50 border border-blue-200 rounded-lg p-4">
          <h3 className="text-sm font-medium text-blue-900 mb-2">💡 Tips para un buen tópico:</h3>
          <ul className="text-sm text-blue-800 space-y-1">
            <li>• Usa un título descriptivo y específico</li>
            <li>• Incluye el código relevante y mensajes de error</li>
            <li>• Menciona qué has intentado hacer para resolver el problema</li>
            <li>• Especifica tu entorno (versiones, sistema operativo, etc.)</li>
          </ul>
        </div>
      </div>
    </div>
  );
};
