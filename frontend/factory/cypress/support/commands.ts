/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
// declare global {
//   namespace Cypress {
//     interface Chainable {
//       login(email: string, password: string): Chainable<void>
//       drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
//     }
//   }
// }

Cypress.Commands.add('fillRawMaterialForm', (name: string, stock: number) => {
  cy.get('#name').clear().type(name)
  cy.get('#stockQuantity').clear().type(stock.toString())
  cy.get('button[type=submit]').click()
})

Cypress.Commands.add('fillProductForm', (name: string, value: number) => {
  cy.get('#name').clear().type(name)
  cy.get('#value').clear().type(value.toString())
})

Cypress.Commands.add('addComposition', (material: string, quantity: number) => {
  cy.get('#rawMaterial option').contains(material).then(($opt) => {
    const val = $opt.attr('value')
    if (val) cy.get('#rawMaterial').select(val)
  })
  cy.get('#quantity').clear().type(quantity.toString())
  cy.contains('button', /adicionar matéria-prima|add composition/i).click()
})

Cypress.Commands.add('switchLanguage', (lang: 'pt' | 'en') => {
  const label = lang === 'pt' ? 'PT' : 'EN'
  cy.get('.lang-btn').contains(label).click()
})


declare global {
  namespace Cypress {
    interface Chainable {
      fillRawMaterialForm(name: string, stock: number): Chainable<void>
      fillProductForm(name: string, value: number): Chainable<void>
      addComposition(material: string, quantity: number): Chainable<void>
      switchLanguage(lang: 'pt' | 'en'): Chainable<void>
    }
  }
}

export {}
