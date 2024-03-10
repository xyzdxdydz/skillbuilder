//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import nameerror.skillbuilder.Configuration.PluginConfiguration;
//import nameerror.skillbuilder.OldCode.Enum.ActionType;
//import nameerror.skillbuilder.SkillBuilder;
//import nameerror.skillbuilder.OldCode.SkillManagement.SkillManager;
//import nameerror.skillbuilder.OldCode.SkillManagement.TargetSelector.Enum.SelectionMode;
//import nameerror.skillbuilder.OldCode.SkillManagement.TargetSelector.TargetSelector;
//import nameerror.skillbuilder.Verbose;
//import org.bukkit.Location;
//import org.bukkit.entity.Entity;
//
//import java.util.*;
//
//public class ObjectManager {
//
//    private static final HashMap<Entity, List<ObjectManager>> objectTable = new HashMap<>();
//    private static final HashMap<Entity, PluginObject> entityObjectTable = new HashMap<>();
//
//    public static void initiate(Entity root, SkillManager rootSkill, ActionType actionType, String name, TargetSelector targetSelector, PluginObject parent) {
//
//        if (actionType.equals(ActionType.SENDMESSAGE)) {
//            if (targetSelector != null) {
//                Set<Entity> targets = targetSelector.getTarget();
//                for (Entity e : targets) {
//                    if (entityObjectTable.containsKey(e)) {
//                        ((PluginObjectEntity) getObjectSkill(e)).receiveMessage(name);
//                    }
//                }
//            }
//            return;
//        } else if (actionType.equals(ActionType.OPERATION)) {
//            switch (name.toUpperCase(Locale.ROOT)) {
//                case "REMOVE":
//                    if (parent instanceof PluginObjectEntity) {
//                        ((PluginObjectEntity) parent).destroy();
//                    }
//                    break;
//                default:
//                    break;
//            }
//            return;
//
//        } else if (actionType.equals(ActionType.SET_TARGET)) {
//            Set<Entity> result = new HashSet<>();
//            switch (name.toUpperCase(Locale.ROOT)) {
//                case "PARENT":
//                    if  (parent != null && parent.getParent() instanceof TargetMarkable) {
//                        result.add(((TargetMarkable) parent).getOwner());
//                        ((TargetMarkable) parent).setTarget(result);
//                        ((TargetMarkable) parent).setTarget(result);
//                    }
//                    break;
//
//                case "CHILD":
//                    if (parent instanceof TargetMarkable) {
//                        for (PluginObject o : parent.getChild()) {
//                            if (o instanceof TargetMarkable) {
//                                result.add(((TargetMarkable) o).getOwner());
//                            }
//                        }
//                        ((TargetMarkable) parent).setTarget(result);
//                    }
//                    break;
//
//                case "SENDER":
//                    break;
//                case "USE_TARGET_SELECTOR":
//                    break;
//            }
//            return;
//        } else if (actionType.equals(ActionType.ADD_TARGET)) {
//            switch (name.toUpperCase(Locale.ROOT)) {
//                case "PARENT":
//
//                    break;
//                case "CHILD":
//                    break;
//                case "SENDER":
//                    break;
//                case "USE_TARGET_SELECTOR":
//                    break;
//            }
//        }
//
//        PluginConfiguration objectList = SkillBuilder.configurationDatabase.get("ObjectConfig");
//
//        String path = name;
//        String objectConfigPath = objectList.getDirPath().replace("\\", "/") + "/" + objectList.getFileName();
//
//        if (name.equals("$BLANK")){
//            return;
//
//        } else if (!objectList.isConfigurationSection(path)) {
//            root.sendMessage(Verbose.objectSectionNotFound(name, objectConfigPath));
//            return;
//        }
//
////        String hh = name;
////        ObjectSkill kk = parent;
////        while (kk != null) {
////            hh = hh.concat(" -> " + kk.getName());
////            kk = kk.getParent();
////        }
////        root.sendMessage(hh);
//
//        PluginObject pluginObject = null;
//
//        if (objectList.getString(path + ".ObjectType") == null) {
//            root.sendMessage(Verbose.objectTypeNotFound(name, objectConfigPath));
//
//        } else if (objectList.getString(path + ".ObjectType").equalsIgnoreCase("TRANSMISSION")) {
//            if (objectList.getString(path + ".TransmissionType").split(" ")[0].equalsIgnoreCase("PROJECTILE")) {
//                ProjectileTransmission p = new ProjectileTransmission(root, objectList.getConfigurationSection(path), parent);
//                pluginObject = p;
//
//                if (targetSelector != null) {
//                    if (targetSelector.getMode().equals(SelectionMode.ENTITY)) {
//                        Set<Entity> targets = targetSelector.getTarget();
//                        for (Entity e : targets) {
//                            p.setDirection(e.getLocation().toVector().subtract(p.getStartLocation().toVector()));
//                            p.buildSkill();
//                        }
//                    } else if (targetSelector.getMode().equals(SelectionMode.RANDOM_LOCATION)) {
//                        Set<Location> locations = targetSelector.getRandomLocation();
//                        for (Location loc : locations) {
//                            p.setDirection(loc.toVector().subtract(p.getStartLocation().toVector()));
//                            p.buildSkill();
//                        }
//                    } else if (targetSelector.getMode().equals(SelectionMode.USE_TARGET)) {
//                        if (parent instanceof TargetMarkable) {
//                            Set<Entity> targets = ((TargetMarkable) parent).getAllTargets();
//                            for (Entity e : targets) {
//                                p.setDirection(e.getLocation().toVector().subtract(p.getStartLocation().toVector()));
//                                p.buildSkill();
//                            }
//                        } else if (parent == null) {
//                            Set<Entity> targets = rootSkill.getAllTargets();
//                            for (Entity e : targets) {
//                                p.setDirection(e.getLocation().toVector().subtract(p.getStartLocation().toVector()));
//                                p.buildSkill();
//                            }
//                        }
//                    }
//
//
//                } else {
//                    p.buildSkill();
//                }
//
//                if (parent != null) {
//                    parent.addChild(pluginObject);
//                }
//
//            } else if (objectList.getString(path + ".TransmissionType").equalsIgnoreCase("LASER")) {
//                LaserTransmission l = new LaserTransmission(root, objectList.getConfigurationSection(path), parent);
//                pluginObject = l;
//
//                if (targetSelector != null) {
//                    if (targetSelector.getMode().equals(SelectionMode.ENTITY)) {
//                        Set<Entity> targets = targetSelector.getTarget();
//                        for (Entity e : targets) {
//                            l.setRayDirection(e.getLocation().toVector().subtract(l.getStartLocation().toVector()));
//                            l.buildSkill();
//                        }
//                    } else if (targetSelector.getMode().equals(SelectionMode.RANDOM_LOCATION)) {
//                        Set<Location> locations = targetSelector.getRandomLocation();
//                        for (Location loc : locations) {
//                            l.setRayDirection(loc.toVector().subtract(l.getStartLocation().toVector()));
//                            l.buildSkill();
//                        }
//
//                    } else if (targetSelector.getMode().equals(SelectionMode.USE_TARGET)) {
//                        if (parent instanceof TargetMarkable) {
//                            Set<Entity> targets = ((TargetMarkable) parent).getAllTargets();
//                            for (Entity e : targets) {
//                                l.setRayDirection(e.getLocation().toVector().subtract(l.getStartLocation().toVector()));
//                                l.buildSkill();
//                            }
//                        } else if (parent == null) {
//                            Set<Entity> targets = rootSkill.getAllTargets();
//                            for (Entity e : targets) {
//                                l.setRayDirection(e.getLocation().toVector().subtract(l.getStartLocation().toVector()));
//                                l.buildSkill();
//                            }
//                        }
//                    }
//
//                } else {
//                    l.buildSkill();
//                }
//
//                if (parent != null) {
//                    parent.addChild(pluginObject);
//                }
//
//            } else if (objectList.getString(path + ".TransmissionType").equalsIgnoreCase("AOE_STATIC")) {
//                StaticAOETransmission as = new StaticAOETransmission(root, objectList.getConfigurationSection(path), parent);
//                pluginObject = as;
//
//                if (targetSelector != null) {
//                    if (targetSelector.getMode().equals(SelectionMode.ENTITY)) {
//                        Set<Entity> targets = targetSelector.getTarget();
//                        for (Entity e : targets) {
//                            as.setDirection((e.getLocation().subtract(as.getStartLocation())).toVector().normalize());
//                            as.buildSkill();
//                        }
//                    } else if (targetSelector.getMode().equals(SelectionMode.RANDOM_LOCATION)) {
//                        Set<Location> locations = targetSelector.getRandomLocation();
//                        for (Location loc : locations) {
//                            as.setDirection((loc.subtract(as.getStartLocation())).toVector().normalize());
//                            as.buildSkill();
//                        }
//                    } else if (targetSelector.getMode().equals(SelectionMode.USE_TARGET)) {
//                        if (parent instanceof TargetMarkable) {
//                            Set<Entity> targets = ((TargetMarkable) parent).getAllTargets();
//                            for (Entity e : targets) {
//                                as.setDirection(e.getLocation().toVector().subtract(as.getStartLocation().toVector()));
//                                as.buildSkill();
//                            }
//                        } else if (parent == null) {
//                            Set<Entity> targets = rootSkill.getAllTargets();
//                            for (Entity e : targets) {
//                                as.setDirection(e.getLocation().toVector().subtract(as.getStartLocation().toVector()));
//                                as.buildSkill();
//                            }
//                        }
//                    }
//
//                } else {
//                    as.buildSkill();
//                }
//
//                if (parent != null) {
//                    parent.addChild(pluginObject);
//                }
//            }
//
//        } else if (objectList.getString(path + ".ObjectType").equalsIgnoreCase("ENTITY")) {
//            if (targetSelector != null) {
//                if (targetSelector.getMode().equals(SelectionMode.ENTITY)) {
//                    Set<Entity> targets = targetSelector.getTarget();
//                    for (Entity e : targets) {
//                        PluginObjectEntity objectEntity = new PluginObjectEntity(root, rootSkill, objectList.getConfigurationSection(path), parent);
//                        objectEntity.setSpawnLocation(e.getLocation());
//                        objectEntity.spawnEntity();
//                        objectEntity.activate();
//                        pluginObject = objectEntity;
//
//                        if (parent != null) {
//                            parent.addChild(pluginObject);
//                        }
//                    }
//                } else if (targetSelector.getMode().equals(SelectionMode.RANDOM_LOCATION)) {
//                    Set<Location> locations = targetSelector.getRandomLocation();
//                    for (Location loc : locations) {
//                        PluginObjectEntity objectEntity = new PluginObjectEntity(root, rootSkill, objectList.getConfigurationSection(path), parent);
//                        objectEntity.setSpawnLocation(loc);
//                        objectEntity.spawnEntity();
//                        objectEntity.activate();
//
//                        if (parent != null) {
//                            parent.addChild(objectEntity);
//                        }
//                    }
//
//                } else if (targetSelector.getMode().equals(SelectionMode.USE_TARGET)) {
//                    if (parent instanceof TargetMarkable) {
//                        Set<Entity> targets = ((TargetMarkable) parent).getAllTargets();
//                        for (Entity e : targets) {
//                            PluginObjectEntity objectEntity = new PluginObjectEntity(root, rootSkill, objectList.getConfigurationSection(path), parent);
//                            objectEntity.setSpawnLocation(e.getLocation());
//                            objectEntity.spawnEntity();
//                            objectEntity.activate();
//                            pluginObject = objectEntity;
//
//                            parent.addChild(pluginObject);
//                        }
//                    } else if (parent == null) {
//                        Set<Entity> targets = rootSkill.getAllTargets();
//                        for (Entity e : targets) {
//                            PluginObjectEntity objectEntity = new PluginObjectEntity(root, rootSkill, objectList.getConfigurationSection(path), parent);
//                            objectEntity.setSpawnLocation(e.getLocation());
//                            objectEntity.spawnEntity();
//                            objectEntity.activate();
//                        }
//                    }
//                }
//
//            } else {
//                PluginObjectEntity objectEntity = new PluginObjectEntity(root, rootSkill, objectList.getConfigurationSection(path), parent);
//                objectEntity.spawnEntity();
//                objectEntity.activate();
//                pluginObject = objectEntity;
//                if (parent != null) {
//                    parent.addChild(pluginObject);
//                }
//            }
//        }
//    }
//
//    public static void registerEntityObject(Entity entityObject, PluginObject pluginObject) {
//        entityObjectTable.put(entityObject, pluginObject);
//    }
//
//    public static void removeEntityObject(Entity entityObject) {
//        entityObjectTable.remove(entityObject);
//    }
//
//    public static PluginObject getObjectSkill(Entity entity) {
//        return entityObjectTable.get(entity);
//    }
//}
