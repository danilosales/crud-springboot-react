import React, { Component } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import {withRouter} from 'react-router-dom';
import ApiService from '../../services/ApiService';

class NavBar extends Component {

  constructor(props) {
    super(props);
    this.logout = this.logout.bind(this);
  }

  style = {
    flexGrow: 1
  };
  
  logout = () => {
    ApiService.logout();
    this.props.history.push('/');
  };

  render() {
    return (
      <div>
        <AppBar position="static">
          <Toolbar>
            <IconButton edge="start" color="inherit" aria-label="Menu">
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" style={this.style}>
              Clientes
            </Typography>
            <Button color="inherit" onClick={this.logout}>
              Logout
            </Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default withRouter(NavBar);
