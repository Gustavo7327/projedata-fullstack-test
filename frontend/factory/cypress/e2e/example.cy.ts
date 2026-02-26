// https://on.cypress.io/api

describe('Home page smoke', () => {
  it('renders main info text', () => {
    cy.visit('/')
    cy.contains('Sistema inteligente').should('be.visible')
  })
})
