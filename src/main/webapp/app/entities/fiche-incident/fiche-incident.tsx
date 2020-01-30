import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './fiche-incident.reducer';
import { IFicheIncident } from 'app/shared/model/fiche-incident.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFicheIncidentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IFicheIncidentState = IPaginationBaseState;

export class FicheIncident extends React.Component<IFicheIncidentProps, IFicheIncidentState> {
  state: IFicheIncidentState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { ficheIncidentList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="fiche-incident-heading">
          <Translate contentKey="autocarsApp.ficheIncident.home.title">Fiche Incidents</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autocarsApp.ficheIncident.home.createLabel">Create new Fiche Incident</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('dateIncident')}>
                  <Translate contentKey="autocarsApp.ficheIncident.dateIncident">Date Incident</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('numeroFiche')}>
                  <Translate contentKey="autocarsApp.ficheIncident.numeroFiche">Numero Fiche</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('incidentCritique')}>
                  <Translate contentKey="autocarsApp.ficheIncident.incidentCritique">Incident Critique</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('lieuIncident')}>
                  <Translate contentKey="autocarsApp.ficheIncident.lieuIncident">Lieu Incident</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('nombreVoyageur')}>
                  <Translate contentKey="autocarsApp.ficheIncident.nombreVoyageur">Nombre Voyageur</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('descriptionIncident')}>
                  <Translate contentKey="autocarsApp.ficheIncident.descriptionIncident">Description Incident</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="autocarsApp.ficheIncident.agentDeclarant">Agent Declarant</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="autocarsApp.ficheIncident.vehicule">Vehicule</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ficheIncidentList.map((ficheIncident, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${ficheIncident.id}`} color="link" size="sm">
                      {ficheIncident.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={ficheIncident.dateIncident} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{ficheIncident.numeroFiche}</td>
                  <td>{ficheIncident.incidentCritique ? 'true' : 'false'}</td>
                  <td>{ficheIncident.lieuIncident}</td>
                  <td>{ficheIncident.nombreVoyageur}</td>
                  <td>{ficheIncident.descriptionIncident}</td>
                  <td>
                    {ficheIncident.agentDeclarant ? (
                      <Link to={`agent-declarant/${ficheIncident.agentDeclarant.id}`}>{ficheIncident.agentDeclarant.nom}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {ficheIncident.vehicule ? (
                      <Link to={`vehicule/${ficheIncident.vehicule.id}`}>{ficheIncident.vehicule.immatriculation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ficheIncident.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ficheIncident.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ficheIncident.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ ficheIncident }: IRootState) => ({
  ficheIncidentList: ficheIncident.entities,
  totalItems: ficheIncident.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FicheIncident);
