export const en = {
  common: {
    homeTitle: 'Home',
    subtitle: 'Inventory Management & Production Calculation',
    description: 'Optimize your production and maximize profits with intelligent inventory management.',
    language: 'Language',
    save: 'Save',
    cancel: 'Cancel',
    delete: 'Delete',
    edit: 'Edit',
    view: 'View',
    create: 'Create',
    back: 'Back',
    search: 'Search',
    loading: 'Loading...',
    error: 'Error',
    success: 'Success',
    warning: 'Warning',
    confirmDelete: 'Are you sure you want to delete this item?',
    noDataFound: 'No data found',
    actions: 'Actions',
    code: 'Code',
    value: 'Value (R$)',
    name: 'Name',
    required: 'Required field'
  },
  navigation: {
    home: 'Home',
    products: 'Products',
    rawMaterials: 'Raw Materials',
    production: 'Production'
  },
  home: {
    welcome: 'Welcome to the Factory Management System',
    systemInfo: 'Intelligent system for managing your production',
    features: 'Features',
    productsDescription: 'Manage your products and their compositions',
    rawMaterialsDescription: 'Manage your raw materials and inventory',
    productionDescription: 'View optimal production suggestions based on inventory',
    startExploring: 'Start Exploring'
  },
  products: {
    title: 'Products Management',
    description: 'Manage your products and their compositions (recipes)',
    addNew: 'New Product',
    noProducts: 'No products found. Create your first one!',
    composition: 'Composition (Recipe)',
    compositions: 'Compositions',
    rawMaterial: 'Raw Material',
    quantity: 'Quantity Required',
    addComposition: 'Add Raw Material',
    removeComposition: 'Remove',
    viewDetails: 'Details',
    editProduct: 'Edit Product',
    deleteProduct: 'Delete Product',
    productDeleted: 'Product deleted successfully',
    productCreated: 'Product created successfully',
    productUpdated: 'Product updated successfully',
    createProduct: 'Create New Product',
    updateProduct: 'Edit Product',
    totalProducts: 'Total Products'
  },
  rawMaterials: {
    title: 'Raw Materials Management',
    description: 'Manage your raw materials and their stock quantities',
    addNew: 'New Raw Material',
    noMaterials: 'No raw materials found. Create your first one!',
    currentStock: 'Current Stock',
    viewDetails: 'Details',
    editMaterial: 'Edit Material',
    deleteMaterial: 'Delete Material',
    materialDeleted: 'Raw material deleted successfully',
    materialCreated: 'Raw material created successfully',
    materialUpdated: 'Raw material updated successfully',
    createMaterial: 'Create New Raw Material',
    updateMaterial: 'Edit Raw Material',
    totalMaterials: 'Total Materials'
  },
  production: {
    title: 'Production Suggestion',
    description: 'AI-powered algorithm to maximize profit based on current inventory',
    calculateSuggestion: 'Calculate Suggestion',
    suggestions: 'Suggested Products',
    product: 'Product',
    quantity: 'Quantity',
    profitValue: 'Profit Value',
    totalProfit: 'Total Profit',
    noSuggestions: 'No suggestions available. Check your inventory and product recipes.',
    algorithmExplanation: 'This system analyzes your current raw material inventory and calculates which products should be manufactured to achieve the maximum possible profit.',
    calculating: 'Calculating...',
    suggestionMessage: 'The optimization algorithm suggests manufacturing {count} product(s) for a total value of {value}'
  },
  forms: {
    validation: {
      required: 'This field is required',
      positiveNumber: 'Must be a positive number',
      validName: 'Please enter a valid name'
    },
    errors: {
      loadingFailed: 'Failed to load data',
      savingFailed: 'Failed to save data',
      deletingFailed: 'Failed to delete item',
      apiError: 'API Error'
    },
    messages: {
      unsavedChanges: 'You have unsaved changes'
    }
  }
}
