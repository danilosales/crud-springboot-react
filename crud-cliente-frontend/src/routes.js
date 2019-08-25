import React from 'react';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';

import ApiService  from './services/ApiService';
import LoginComponent from './components/Login';
import ListClienteComponent from './components/cliente/ListClienteComponent';
import EditarClienteComponent from './components/cliente/EditarClienteComponent';


const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      ApiService.isUserLoggedIn() ? (
        <Component {...props} />
      ) : (
        <Redirect to={{ pathname: "/", state: { from: props.location } }} />
      )
    }
  />
);

const Routes = () => (
  <BrowserRouter>
    <Switch>
      <Route exact path="/" component={LoginComponent} />
      <PrivateRoute exact path="/clientes" component={ListClienteComponent} />
      <PrivateRoute exact path="/clientes/novo" component={EditarClienteComponent} />
      <PrivateRoute exact path="/clientes/:id" component={EditarClienteComponent} />
      <Route path="*" component={() => <h1>Página não encontrada</h1>} />
    </Switch>
  </BrowserRouter>
);

export default Routes;