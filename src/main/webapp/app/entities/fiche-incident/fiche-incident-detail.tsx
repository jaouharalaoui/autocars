import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fiche-incident.reducer';
import { IFicheIncident } from 'app/shared/model/fiche-incident.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFicheIncidentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FicheIncidentDetail extends React.Component<IFicheIncidentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ficheIncidentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="autocarsApp.ficheIncident.detail.title">FicheIncident</Translate> [<b>{ficheIncidentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="dateIncident">
                <Translate contentKey="autocarsApp.ficheIncident.dateIncident">Date Incident</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ficheIncidentEntity.dateIncident} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="numeroFiche">
                <Translate contentKey="autocarsApp.ficheIncident.numeroFiche">Numero Fiche</Translate>
              </span>
            </dt>
            <dd>{ficheIncidentEntity.numeroFiche}</dd>
            <dt>
              <span id="incidentCritique">
                <Translate contentKey="autocarsApp.ficheIncident.incidentCritique">Incident Critique</Translate>
              </span>
            </dt>
            <dd>{ficheIncidentEntity.incidentCritique ? 'true' : 'false'}</dd>
            <dt>
              <span id="lieuIncident">
                <Translate contentKey="autocarsApp.ficheIncident.lieuIncident">Lieu Incident</Translate>
              </span>
            </dt>
            <dd>{ficheIncidentEntity.lieuIncident}</dd>
            <dt>
              <span id="nombreVoyageur">
                <Translate contentKey="autocarsApp.ficheIncident.nombreVoyageur">Nombre Voyageur</Translate>
              </span>
            </dt>
            <dd>{ficheIncidentEntity.nombreVoyageur}</dd>
            <dt>
              <span id="descriptionIncident">
                <Translate contentKey="autocarsApp.ficheIncident.descriptionIncident">Description Incident</Translate>
              </span>
            </dt>
            <dd>{ficheIncidentEntity.descriptionIncident}</dd>
            <dt>
              <Translate contentKey="autocarsApp.ficheIncident.agentDeclarant">Agent Declarant</Translate>
            </dt>
            <dd>{ficheIncidentEntity.agentDeclarant ? ficheIncidentEntity.agentDeclarant.nom : ''}</dd>
            <dt>
              <Translate contentKey="autocarsApp.ficheIncident.vehicule">Vehicule</Translate>
            </dt>
            <dd>{ficheIncidentEntity.vehicule ? ficheIncidentEntity.vehicule.immatriculation : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/fiche-incident" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/fiche-incident/${ficheIncidentEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ ficheIncident }: IRootState) => ({
  ficheIncidentEntity: ficheIncident.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FicheIncidentDetail);
