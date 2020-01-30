import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AgentDeclarant from './agent-declarant';
import Chauffeur from './chauffeur';
import Vehicule from './vehicule';
import FicheIncident from './fiche-incident';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/agent-declarant`} component={AgentDeclarant} />
      <ErrorBoundaryRoute path={`${match.url}/chauffeur`} component={Chauffeur} />
      <ErrorBoundaryRoute path={`${match.url}/vehicule`} component={Vehicule} />
      <ErrorBoundaryRoute path={`${match.url}/fiche-incident`} component={FicheIncident} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
