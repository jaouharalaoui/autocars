import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Chauffeur from './chauffeur';
import ChauffeurDetail from './chauffeur-detail';
import ChauffeurUpdate from './chauffeur-update';
import ChauffeurDeleteDialog from './chauffeur-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChauffeurUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChauffeurUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChauffeurDetail} />
      <ErrorBoundaryRoute path={match.url} component={Chauffeur} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ChauffeurDeleteDialog} />
  </>
);

export default Routes;
