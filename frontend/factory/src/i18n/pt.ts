export const pt = {
  common: {
    homeTitle: 'Início',
    subtitle: 'Gerenciamento de Estoque e Cálculo de Produção',
    description: 'Otimize sua produção e maximize lucros com gerenciamento inteligente de estoque.',
    language: 'Idioma',
    save: 'Salvar',
    cancel: 'Cancelar',
    delete: 'Deletar',
    edit: 'Editar',
    view: 'Visualizar',
    create: 'Criar',
    back: 'Voltar',
    search: 'Buscar',
    loading: 'Carregando...',
    error: 'Erro',
    success: 'Sucesso',
    warning: 'Aviso',
    confirmDelete: 'Tem certeza que deseja deletar este item?',
    noDataFound: 'Nenhum dado encontrado',
    actions: 'Ações',
    code: 'Código',
    value: 'Valor (R$)',
    name: 'Nome',
    required: 'Campo obrigatório'
  },
  navigation: {
    home: 'Início',
    products: 'Produtos',
    rawMaterials: 'Matérias-primas',
    production: 'Produção'
  },
  home: {
    welcome: 'Bem-vindo ao Sistema de Gerenciamento Fabril',
    systemInfo: 'Sistema inteligente para gerenciar sua produção',
    features: 'Funcionalidades',
    productsDescription: 'Gerencie seus produtos e suas composições',
    rawMaterialsDescription: 'Gerencie suas matérias-primas e estoque',
    productionDescription: 'Veja sugestões de produção otimizadas com base no estoque',
    startExploring: 'Começar a Explorar'
  },
  products: {
    title: 'Gerenciamento de Produtos',
    description: 'Gerencie seus produtos e suas composições (receitas)',
    addNew: 'Novo Produto',
    noProducts: 'Nenhum produto encontrado. Crie o seu primeiro!',
    composition: 'Composição (Receita)',
    compositions: 'Composições',
    rawMaterial: 'Matéria-prima',
    quantity: 'Quantidade Necessária',
    addComposition: 'Adicionar Matéria-prima',
    removeComposition: 'Remover',
    viewDetails: 'Detalhes',
    editProduct: 'Editar Produto',
    deleteProduct: 'Deletar Produto',
    productDeleted: 'Produto deletado com sucesso',
    productCreated: 'Produto criado com sucesso',
    productUpdated: 'Produto atualizado com sucesso',
    createProduct: 'Criar Novo Produto',
    updateProduct: 'Editar Produto',
    totalProducts: 'Total de Produtos'
  },
  rawMaterials: {
    title: 'Gerenciamento de Matérias-primas',
    description: 'Gerencie suas matérias-primas e quantidades em estoque',
    addNew: 'Nova Matéria-prima',
    noMaterials: 'Nenhuma matéria-prima encontrada. Crie a sua primeira!',
    currentStock: 'Estoque Atual',
    viewDetails: 'Detalhes',
    editMaterial: 'Editar Matéria-prima',
    deleteMaterial: 'Deletar Matéria-prima',
    materialDeleted: 'Matéria-prima deletada com sucesso',
    materialCreated: 'Matéria-prima criada com sucesso',
    materialUpdated: 'Matéria-prima atualizada com sucesso',
    createMaterial: 'Criar Nova Matéria-prima',
    updateMaterial: 'Editar Matéria-prima',
    totalMaterials: 'Total de Matérias-primas'
  },
  production: {
    title: 'Sugestão de Produção',
    description: 'Algoritmo aprimorado para maximizar lucro com base no estoque atual',
    calculateSuggestion: 'Calcular Sugestão',
    suggestions: 'Produtos Sugeridos',
    product: 'Produto',
    quantity: 'Quantidade',
    profitValue: 'Valor de Lucro',
    totalProfit: 'Lucro Total',
    noSuggestions: 'Nenhuma sugestão disponível. Verifique seu estoque e receitas.',
    algorithmExplanation: 'Este sistema analisa seu estoque atual de matérias-primas e calcula quais produtos devem ser fabricados para obter o lucro máximo possível.',
    calculating: 'Calculando...',
    suggestionMessage: 'O algoritmo de otimização sugere fabricar {count} produto(s) para um valor total de {value}'
  },
  forms: {
    validation: {
      required: 'Este campo é obrigatório',
      positiveNumber: 'Deve ser um número positivo',
      validName: 'Por favor, digite um nome válido'
    },
    errors: {
      loadingFailed: 'Falha ao carregar dados',
      savingFailed: 'Falha ao salvar dados',
      deletingFailed: 'Falha ao deletar item',
      apiError: 'Erro na API'
    },
    messages: {
      unsavedChanges: 'Você tem alterações não salvas'
    }
  }
}
