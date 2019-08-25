import React, { Component } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableRowCliente from './TableRowCliente';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

import ApiService from '../../../services/ApiService';
import NavBar from '../../Navbar';

class ListClienteComponent extends Component {
  state = {
    clientes: [],
    message: null
  };
  constructor(props) {
    super(props);
    this.novoCliente = this.novoCliente.bind(this);
    this.buscarListaClientes = this.buscarListaClientes.bind(this);
  }

  componentDidMount() {
    this.buscarListaClientes();
  }

  async buscarListaClientes() {
    try {
      const response = await ApiService.getTodosClientes();
      this.setState({ clientes: response.data });
    } catch (err) {
      this.setState({
        message:
        "Houve um problema ao listar os clientes."
      })
    }
  }

  novoCliente(e) {
    this.props.history.push('/clientes/novo');
  }

  popularLinhas() {
    return this.state.clientes.map((object, i) => {
      return <TableRowCliente obj={object} key={i} />
    });
  }

  render() {
    const isPermitWrite = ApiService.isUserAdmin();
    return (
      <div>
        <NavBar/> 
        <br />
        <br />
        <Typography variant="h4" style={style}>
          Lista de Clientes
        </Typography>

        { isPermitWrite &&
          <Button
          variant="contained"
          color="primary"
          onClick={() => this.novoCliente()}
          >
            Novo Cliente
          </Button>
        }
        
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nome</TableCell>
              <TableCell align="left">CPF</TableCell>
              <TableCell align="left">Cidade/UF</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            { this.popularLinhas() }
          </TableBody>
        </Table>
      </div>
    );
  }
}

const style = {
  display: 'flex',
  justifyContent: 'center'
};

export default ListClienteComponent;
