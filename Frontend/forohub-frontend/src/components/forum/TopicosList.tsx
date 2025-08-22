import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { topicoService } from '../../services/topicos';
import { Search, Calendar, User, MessageCircle, ChevronLeft, ChevronRight } from 'lucide-react';
import type { Topico, PaginatedResponse } from '../../types';

export const TopicosList: React.FC = () => {
  const [topicos, setTopicos] = useState<PaginatedResponse<Topico> | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(0);

  const loadTopicos = async (page: number = 0, search?: string) => {
    setIsLoading(true);
    setError('');
    
    try {
      let result: PaginatedResponse<Topico>;
      
      if (search && search.trim()) {
        result = await topicoService.searchTopicos(search, page);
      } else {
        result = await topicoService.getTopicos(page);
      }
      
      setTopicos(result);
      setCurrentPage(page);
    } catch (err: any) {
      setError(err.message || 'Error al cargar los tópicos');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadTopicos(0, searchTerm);
  }, []);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setCurrentPage(0);
    loadTopicos(0, searchTerm);
  };

  const handlePageChange = (newPage: number) => {
    loadTopicos(newPage, searchTerm);
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'short',
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

  if (isLoading && !topicos) {
    return (
      <div className="flex justify-center items-center py-12">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto">
      {/* Search */}
      <div className="mb-6">
        <form onSubmit={handleSearch} className="flex gap-2">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Buscar tópicos por título o contenido..."
              className="input-field pl-10"
            />
          </div>
          <button type="submit" className="btn-primary">
            Buscar
          </button>
        </form>
      </div>

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg mb-6">
          {error}
        </div>
      )}

      {/* Topics list */}
      {topicos && topicos.content.length > 0 ? (
        <div className="space-y-4">
          {topicos.content.map((topico) => (
            <div key={topico.id} className="card hover:shadow-md transition-shadow">
              <div className="flex justify-between items-start mb-3">
                <Link
                  to={`/topico/${topico.id}`}
                  className="text-lg font-semibold text-gray-900 hover:text-primary-600 transition-colors"
                >
                  {topico.titulo}
                </Link>
                <span className={`px-2 py-1 rounded-full text-xs font-medium ${getStatusColor(topico.status)}`}>
                  {topico.status}
                </span>
              </div>
              
              <p className="text-gray-600 mb-4 line-clamp-2">
                {topico.mensaje}
              </p>
              
              <div className="flex items-center justify-between text-sm text-gray-500">
                <div className="flex items-center space-x-4">
                  <div className="flex items-center space-x-1">
                    <User className="w-4 h-4" />
                    <span>{topico.autor.nombre}</span>
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
                
                <div className="text-xs bg-gray-100 px-2 py-1 rounded">
                  {topico.curso.nombre}
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <MessageCircle className="mx-auto w-12 h-12 text-gray-400 mb-4" />
          <h3 className="text-lg font-medium text-gray-900 mb-2">
            {searchTerm ? 'No se encontraron tópicos' : 'No hay tópicos aún'}
          </h3>
          <p className="text-gray-600">
            {searchTerm 
              ? 'Intenta con otros términos de búsqueda' 
              : 'Sé el primero en crear un tópico'
            }
          </p>
        </div>
      )}

      {/* Pagination */}
      {topicos && topicos.totalPages > 1 && (
        <div className="flex justify-center items-center space-x-2 mt-8">
          <button
            onClick={() => handlePageChange(currentPage - 1)}
            disabled={currentPage === 0 || isLoading}
            className="p-2 rounded-lg border border-gray-300 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
          >
            <ChevronLeft className="w-4 h-4" />
          </button>
          
          <span className="px-4 py-2 text-sm text-gray-600">
            Página {currentPage + 1} de {topicos.totalPages}
          </span>
          
          <button
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={currentPage >= topicos.totalPages - 1 || isLoading}
            className="p-2 rounded-lg border border-gray-300 disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
          >
            <ChevronRight className="w-4 h-4" />
          </button>
        </div>
      )}
    </div>
  );
};
