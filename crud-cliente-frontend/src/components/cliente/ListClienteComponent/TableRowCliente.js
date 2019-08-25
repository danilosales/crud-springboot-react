import React, { Component } from 'react';

import { TableCell, TableRow } from '@material-ui/core';
import { Link } from 'react-router-dom';
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from '@material-ui/icons/Delete';

import ApiService from '../../../services/ApiService';
import { cpfMask } from '../../../util/mask';

class TableRowCliente extends Component {
  constructor(props) {
    super(props);
    this.excluirCliente = this.excluirCliente.bind(this);
  }

  async excluirCliente() {
    if (window.confirm('Deseja realmente excluir esse registro?')) {
      try {
        await ApiService.excluirClientePorId(this.props.obj.id);
        window.location.reload();
      } catch (err) {
        console.log(err);
      }
    }
  }

  editarCliente(id) {
    this.props.history.push(`/clientes/${id}`);
  }

  render() {
    const isPermitWrite = ApiService.isUserAdmin();
    return (
      <TableRow>
        <TableCell align="left">{this.props.obj.nome}</TableCell>
        <TableCell align="left">{cpfMask(this.props.obj.cpf)}</TableCell>
        <TableCell align="left">{`${this.props.obj.cidade}/${this.props.obj.uf}` }</TableCell>
        { isPermitWrite && <TableCell align="right">
          <Link to={`/clientes/${this.props.obj.id}`}>
            <CreateIcon />
          </Link>
          </TableCell>
        }
        { isPermitWrite && 
          <TableCell align="right" onClick={() => this.excluirCliente()}>
            <DeleteIcon />
          </TableCell>
        }
      </TableRow>
    );
  }
}

export default TableRowCliente;
