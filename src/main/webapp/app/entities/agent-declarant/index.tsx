import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AgentDeclarant from './agent-declarant';
import AgentDeclarantDetail from './agent-declarant-detail';
import AgentDeclarantUpdate from './agent-declarant-update';
import AgentDeclarantDeleteDialog from './agent-declarant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AgentDeclarantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AgentDeclarantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AgentDeclarantDetail} />
      <ErrorBoundaryRoute path={match.url} component={AgentDeclarant} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AgentDeclarantDeleteDialog} />
  </>
);

export default Routes;
