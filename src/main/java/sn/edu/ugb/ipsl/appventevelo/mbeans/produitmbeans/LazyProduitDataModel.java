package sn.edu.ugb.ipsl.appventevelo.mbeans.produitmbeans;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.primefaces.model.filter.FilterConstraint;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyProduitDataModel extends LazyDataModel<Produit> {

    private final List<Produit> datasource;

    public LazyProduitDataModel(List<Produit> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Produit getRowData(String rowKey) {
        for (Produit produit : datasource) {
            if (produit.getId() == Integer.parseInt(rowKey)) {
                return produit;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Produit produit) {
        return String.valueOf(produit.getId());
    }


    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .filter(produit -> filter(produit, filterBy))
                .count();
    }

    @Override
    public List<Produit> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        // Apply offset & filters
        List<Produit> produits = datasource.stream()
                .filter(produit -> filter(produit, filterBy))
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());

        // Sort
        if (!sortBy.isEmpty()) {
            Comparator<Produit> comparator = (produit1, produit2) -> {
                for (SortMeta sortMeta : sortBy.values()) {
                    String sortField = sortMeta.getField();
                    int sortOrder = convertSortOrderToInt(sortMeta.getOrder()); // Convert SortOrder to int

                    if ("marque".equals(sortField)) {
                        String marque1 = (produit1.getMarque().getNom() != null) ? produit1.getMarque().getNom() : "";
                        String marque2 = (produit2.getMarque().getNom() != null) ? produit2.getMarque().getNom() : "";
                        int result = marque1.compareToIgnoreCase(marque2);
                        if (result != 0) {
                            return (sortOrder == 1) ? result : -result;
                        }
                    } else if ("categorie".equals(sortField)) {
                        String categorie1 = (produit1.getCategorie().getNom() != null) ? produit1.getCategorie().getNom() : "";
                        String categorie2 = (produit2.getCategorie().getNom() != null) ? produit2.getCategorie().getNom() : "";
                        int result = categorie1.compareToIgnoreCase(categorie2);
                        if (result != 0) {
                            return (sortOrder == 1) ? result : -result;
                        }
                    } else if ("nom".equals(sortField)) {
                        String nom1 = (produit1.getNom() != null) ? produit1.getNom() : "";
                        String nom2 = (produit2.getNom() != null) ? produit2.getNom() : "";
                        int result = nom1.compareToIgnoreCase(nom2);
                        if (result != 0) {
                            return (sortOrder == 1) ? result : -result;
                        }
                    } else if ("annee_model".equals(sortField)) {
                        short anneeModel1 = produit1.getAnnee_model();
                        short anneeModel2 = produit2.getAnnee_model();
                        int result = Short.compare(anneeModel1, anneeModel2);
                        if (result != 0) {
                            return (sortOrder == 1) ? result : -result;
                        }
                    } else if ("prix_depart".equals(sortField)) {
                        BigDecimal prixDepart1 = produit1.getPrix_depart();
                        BigDecimal prixDepart2 = produit2.getPrix_depart();
                        int result = prixDepart1.compareTo(prixDepart2);
                        if (result != 0) {
                            return (sortOrder == 1) ? result : -result;
                        }
                    }
                }
                return 0;
            };
            produits.sort(comparator);
        }

        return produits;
    }

    private int convertSortOrderToInt(SortOrder sortOrder) {
        return (sortOrder == SortOrder.ASCENDING) ? 1 : -1;
    }


    private boolean filter(Produit produit, Map<String, FilterMeta> filterBy) {
        boolean matching = true;
        for (Map.Entry<String, FilterMeta> entry : filterBy.entrySet()) {
            String field = entry.getKey();
            FilterMeta filter = entry.getValue();
            Object filterValue = filter.getFilterValue();

            if ("marque".equals(field)) {
                String marque = (produit.getMarque().getNom() != null) ? produit.getMarque().getNom() : "";
                matching = filterConstraint(filter, marque, filterValue);
            } else if ("categorie".equals(field)) {
                String categorie = (produit.getCategorie().getNom() != null) ? produit.getCategorie().getNom() : "";
                matching = filterConstraint(filter, categorie, filterValue);
            } else if ("nom".equals(field)) {
                String nom = (produit.getNom() != null) ? produit.getNom() : "";
                matching = filterConstraint(filter, nom, filterValue);
            } else if ("annee_model".equals(field)) {
                short anneeModel = produit.getAnnee_model();
                matching = filterConstraint(filter, anneeModel, filterValue);
            } else if ("prix_depart".equals(field)) {
                BigDecimal prixDepart = produit.getPrix_depart();
                matching = filterConstraint(filter, prixDepart, filterValue);
            }

            if (!matching) {
                break;
            }
        }
        return matching;
    }

    private boolean filterConstraint(FilterMeta filter, String columnValue, Object filterValue) {
        FilterConstraint constraint = filter.getConstraint();
        String filterString = filterValue.toString().toLowerCase();
        String columnStringValue = columnValue.toLowerCase();

        // Vérifiez si la valeur de la colonne contient la sous-chaîne filtrée

        try {
            return columnStringValue.contains(filterString);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean filterConstraint(FilterMeta filter, short columnValue, Object filterValue) {
        // Convertir la valeur du filtre en chaîne et assurez-vous qu'elle est en minuscules
        String filterString = filterValue.toString().toLowerCase();

        // Convertir la valeur de la colonne en chaîne
        String columnStringValue = Short.toString(columnValue);

        try {

            return columnStringValue.contains(filterString);

        } catch (Exception e) {
            return false;
        }
    }

    private boolean filterConstraint(FilterMeta filter, BigDecimal columnValue, Object filterValue) {
        // Convertir la valeur du filtre en chaîne et assurez-vous qu'elle est en minuscules
        String filterString = filterValue.toString().toLowerCase();

        // Convertir la valeur de la colonne en chaîne
        String columnStringValue = columnValue.toString().toLowerCase();

        try {

            return columnStringValue.contains(filterString);

        } catch (Exception e) {
            return false;
        }
    }

}
