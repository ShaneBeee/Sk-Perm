package tk.shanebee.skperm.permPlugins.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import tk.shanebee.skperm.SkPerm;
import tk.shanebee.skperm.utils.api.API;

@Name("Permission: Group Weight")
@Description("Set the weight of a group, also supports add, remove, reset and get. " +
        "[Requires a permission plugin, Currently supports PEX, LuckPerms and UltraPermissions]")
@Examples({"set weight of group \"owner\" to 1", "set weight of group \"admin\" to 100",
        "reset weight of group \"default\"", "set {_weight} to weight of group \"admin\""})
@Since("2.0.0")
public class ExprGroupWeight extends SimpleExpression<Number> {

    private API api = SkPerm.getAPI();

    static {
        Skript.registerExpression(ExprGroupWeight.class, Number.class, ExpressionType.PROPERTY,
                "weight of group %string%", "group %string%'s weight");
    }

    private Expression<String> group;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET || mode == ChangeMode.ADD) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    protected Number[] get(Event e) {
        return CollectionUtils.array(api.getGroupWeight(this.group.getSingle(e)));
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        String group = this.group.getSingle(e);
        Number weight = delta != null ? (Number) delta[0] : 0;
        int oldWeight;
        switch (mode) {
            case SET:
                api.setGroupWeight(group, weight.intValue());
                break;
            case ADD:
                oldWeight = api.getGroupWeight(group);
                api.setGroupWeight(group, (oldWeight + weight.intValue()));
                break;
            case REMOVE:
                oldWeight = api.getGroupWeight(group);
                api.setGroupWeight(group, (oldWeight - weight.intValue()));
                break;
            case RESET:
                api.setGroupWeight(group, 0);
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean d) {
        return "weight of group " + group.toString(e, d);
    }
}
