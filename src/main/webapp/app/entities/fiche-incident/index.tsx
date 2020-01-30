import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FicheIncident from './fiche-incident';
import FicheIncidentDetail from './fiche-incident-detail';
import FicheIncidentUpdate from './fiche-incident-update';
import FicheIncidentDeleteDialog from './fiche-incident-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FicheIncidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FicheIncidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FicheIncidentDetail} />
      <ErrorBoundaryRoute path={match.url} component={FicheIncident} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FicheIncidentDeleteDialog} />
  </>
);

export default Routes;
