/// <reference types="cypress" />

describe('Application flows', () => {
  beforeEach(() => {
    const materials: Array<any> = [
      { code: 1, name: 'Ferro', stockQuantity: 100 },
      { code: 2, name: 'Plástico', stockQuantity: 50 }
    ]
    const products: Array<any> = []

    cy.intercept('GET', '**/raw-materials', (req) => {
      req.reply({ statusCode: 200, body: materials })
    }).as('getMaterials')

    cy.intercept('POST', '**/raw-materials', (req) => {
      const newMat = { code: materials.length + 1, ...req.body }
      materials.push(newMat)
      req.reply({ statusCode: 201, body: newMat })
    }).as('postMaterial')

    cy.intercept('GET', '**/products', (req) => {
      req.reply({ statusCode: 200, body: products })
    }).as('getProducts')

    cy.intercept('POST', '**/products', (req) => {
      const newProd = { code: products.length + 1, ...req.body }
      products.push(newProd)
      req.reply({ statusCode: 201, body: newProd })
    }).as('postProduct')
  })

  describe('language switching', () => {
    it('changes the UI language and persists setting', () => {
      cy.visit('/')
      cy.contains('🇧🇷 PT').should('be.visible')
      cy.switchLanguage('en')
      cy.contains('Home').should('exist')
      cy.reload()
      cy.contains('Home').should('exist')
      cy.switchLanguage('pt')
      cy.contains('Início').should('exist')
    })
  })

  describe('raw material form', () => {
    it('creates a material after valid submission', () => {
      cy.visit('/raw-materials/new')

      cy.fillRawMaterialForm('Madeira', 10)
      cy.wait('@postMaterial')

      cy.url().should('include', '/raw-materials')
      cy.contains('Madeira').should('exist')
    })
  })

  describe('product creation', () => {
    it('allows adding compositions and submits product', () => {
      cy.visit('/products/new')
      cy.wait('@getMaterials')

      cy.get('button[type=submit]').should('be.disabled')

      cy.fillProductForm('Produto A', 25.5)

      cy.addComposition('Ferro (', 2)

      cy.get('.compositions-table tbody tr').should('have.length', 1)

      cy.get('button[type=submit]').should('not.be.disabled')

      cy.get('button[type=submit]').click()
      cy.wait('@postProduct')
      cy.url().should('include', '/products')
      cy.contains('Produto A').should('exist')
    })
  })

  describe('navigation and usability', () => {
    it('lets the user navigate through pages using header', () => {
      cy.visit('/')
      cy.contains('Produtos').click()
      cy.url().should('include', '/products')
      cy.contains('Matérias-primas').click()
      cy.url().should('include', '/raw-materials')
      cy.contains('Produção').click()
      cy.url().should('include', '/production')
    })
  })
})
