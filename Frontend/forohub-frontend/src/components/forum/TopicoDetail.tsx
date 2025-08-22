import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { topicoService } from '../../services/topicos';
import { useAuth } from '../../context/AuthContext';
import { ArrowLeft, Calendar, User, MessageCircle, Edit, Trash2 } from 'lucide-react';
import type { Topico } from '../../types';

export const TopicoDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [topico, setTopico] = useState<Topico | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      loadTopico(parseInt(id));
    }
  }, [id]);

  const loadTopico = async (topicoId: number) => {
    setIsLoading(true);
    setError('');
    
    try {
      const data = await topicoService.getTopicoById(topicoId);
      setTopico(data);
    } catch (err: any) {
      setError(err.message || 'Error al cargar el tópico');
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!topico || !window.confirm('¿Estás seguro de que quieres eliminar este tópico?')) {
      return;
    }

    try {
      await topicoService.deleteTopico(topico.id);
      navigate('/');
    } catch (err: any) {
      setError(err.message || 'Error al eliminar el tópico');
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'RESUELTO':
        return 'bg-green-100 text-green-800';
      case 'CERRADO':
        return 'bg-gray-100 text-gray-800';
      default:
        return 'bg-blue-100 text-blue-800';
    }
  };

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  if (error || !topico) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">
            {error || 'Tópico no encontrado'}
          </h2>
          <button
            onClick={() => navigate('/')}
            className="btn-primary"
          >
            Volver al inicio
          </button>
        </div>
      </div>
    );
  }

  const isAuthor = user && user.id.toString() === topico.autor.id.toString();

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-4xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-6">
          <button
            onClick={() => navigate('/')}
            className="flex items-center space-x-2 text-gray-600 hover:text-gray-900 mb-4"
          >
            <ArrowLeft className="w-4 h-4" />
            <span>Volver a los tópicos</span>
          </button>
        </div>

        {/* Topico Card */}
        <div className="card mb-6">
          <div className="flex justify-between items-start mb-4">
            <div className="flex-1">
              <div className="flex items-center space-x-3 mb-2">
                <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(topico.status)}`}>
                  {topico.status}
                </span>
                <div className="text-sm bg-gray-100 px-2 py-1 rounded">
                  {topico.curso.nombre}
                </div>
              </div>
              <h1 className="text-2xl font-bold text-gray-900 mb-4">
                {topico.titulo}
              </h1>
            </div>
            
            {isAuthor && (
              <div className="flex space-x-2 ml-4">
                <button
                  onClick={() => navigate(`/topico/${topico.id}/editar`)}
                  className="p-2 text-gray-400 hover:text-blue-600 rounded-lg hover:bg-blue-50"
                  title="Editar tópico"
                >
                  <Edit className="w-4 h-4" />
                </button>
                <button
                  onClick={handleDelete}
                  className="p-2 text-gray-400 hover:text-red-600 rounded-lg hover:bg-red-50"
                  title="Eliminar tópico"
                >
                  <Trash2 className="w-4 h-4" />
                </button>
              </div>
            )}
          </div>

          <div className="prose max-w-none mb-6">
            <p className="text-gray-700 whitespace-pre-wrap">
              {topico.mensaje}
            </p>
          </div>

          <div className="flex items-center justify-between text-sm text-gray-500 pt-4 border-t border-gray-200">
            <div className="flex items-center space-x-4">
              <div className="flex items-center space-x-1">
                <User className="w-4 h-4" />
                <span className="font-medium">{topico.autor.nombre}</span>
              </div>
              <div className="flex items-center space-x-1">
                <Calendar className="w-4 h-4" />
                <span>{formatDate(topico.fechaCreacion)}</span>
              </div>
              <div className="flex items-center space-x-1">
                <MessageCircle className="w-4 h-4" />
                <span>{topico.respuestas?.length || 0} respuestas</span>
              </div>
            </div>
          </div>
        </div>

        {/* Respuestas */}
        <div className="space-y-4">
          <h2 className="text-xl font-bold text-gray-900">
            Respuestas ({topico.respuestas?.length || 0})
          </h2>

          {topico.respuestas && topico.respuestas.length > 0 ? (
            topico.respuestas.map((respuesta) => (
              <div key={respuesta.id} className="card">
                <div className="flex justify-between items-start mb-3">
                  <div className="flex items-center space-x-2">
                    <div className="w-8 h-8 bg-gray-200 rounded-full flex items-center justify-center">
                      <User className="w-4 h-4 text-gray-600" />
                    </div>
                    <div>
                      <p className="font-medium text-gray-900">{respuesta.autor.nombre}</p>
                      <p className="text-xs text-gray-500">{formatDate(respuesta.fechaCreacion)}</p>
                    </div>
                  </div>
                  
                  {respuesta.solucion && (
                    <span className="px-2 py-1 bg-green-100 text-green-800 text-xs font-medium rounded-full">
                      ✓ Solución
                    </span>
                  )}
                </div>
                
                <p className="text-gray-700 whitespace-pre-wrap">
                  {respuesta.mensaje}
                </p>
              </div>
            ))
          ) : (
            <div className="text-center py-8 bg-white rounded-lg border border-gray-200">
              <MessageCircle className="mx-auto w-12 h-12 text-gray-400 mb-4" />
              <h3 className="text-lg font-medium text-gray-900 mb-2">
                No hay respuestas aún
              </h3>
              <p className="text-gray-600">
                Sé el primero en responder a esta pregunta
              </p>
            </div>
          )}
        </div>

        {/* Placeholder para responder */}
        <div className="mt-6 card">
          <h3 className="text-lg font-medium text-gray-900 mb-4">
            Responder a este tópico
          </h3>
          <div className="bg-gray-50 border border-gray-200 rounded-lg p-4 text-center">
            <p className="text-gray-600">
              La funcionalidad de responder estará disponible próximamente
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
