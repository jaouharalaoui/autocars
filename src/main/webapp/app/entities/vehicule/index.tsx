import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vehicule from './vehicule';
import VehiculeDetail from './vehicule-detail';
import VehiculeUpdate from './vehicule-update';
import VehiculeDeleteDialog from './vehicule-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VehiculeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VehiculeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VehiculeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Vehicule} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VehiculeDeleteDialog} />
  </>
);

export default Routes;
